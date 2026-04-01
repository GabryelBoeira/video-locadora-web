package io.github.gabryel.videolocadora.service.rent;

import io.github.gabryel.videolocadora.configuration.Messages;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.rental.RentalCreateDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalDetailDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalItemReturnDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalReturnDTO;
import io.github.gabryel.videolocadora.model.entity.RentalEntity;
import io.github.gabryel.videolocadora.model.entity.RentalItemEntity;
import io.github.gabryel.videolocadora.model.enums.RentalStatus;
import io.github.gabryel.videolocadora.model.mapper.rent.RentalMapper;
import io.github.gabryel.videolocadora.repository.MovieRepository;
import io.github.gabryel.videolocadora.repository.RentalRepository;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;


@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CustomerRepository customerRepository;
    private final MovieRepository movieRepository;
    private final RentalMapper rentalMapper;
    private final Messages messages;

    public RentalService(
            RentalRepository rentalRepository,
            CustomerRepository customerRepository,
            MovieRepository movieRepository,
            RentalMapper rentalMapper, Messages messages
    ) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.movieRepository = movieRepository;
        this.rentalMapper = rentalMapper;
        this.messages = messages;
    }

    @Transactional
    public RentalDetailDTO create(RentalCreateDTO dto) throws BusinessException {
        var customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new BusinessException(messages.getMessage("cliente.nao.encontrado")));

        if (customer.getBirthDate() == null) {
            throw new BusinessException(messages.getMessage("cliente.sem.data.nascimento"));
        }

        int customerAge = Period.between(customer.getBirthDate(), LocalDate.now(ZoneId.systemDefault())).getYears();

        var movies = movieRepository.findAllById(dto.movieIds());
        if (movies.size() != dto.movieIds().size()) {
            throw new BusinessException(messages.getMessage("filme.nao.encontrado"));
        }

        var unavailable = movies.stream().filter(m -> !m.getAvailable()).toList();
        if (!unavailable.isEmpty()) {
            throw new BusinessException(messages.getMessage("filme.indisponivel"));
        }

        var blockedByAge = movies.stream()
                .filter(m -> m.getContentRating() != null && customerAge < m.getContentRating().minAge())
                .map(m -> String.format("'%s' (rating: %s, mín: %d, cliente: %d)",
                        m.getTitle(),
                        m.getContentRating().name(),
                        m.getContentRating().minAge(),
                        customerAge
                ))
                .toList();

        if (!blockedByAge.isEmpty()) {
            throw new BusinessException(messages.getMessage("cliente.idade.insuficiente", String.join(", ", blockedByAge)));
        }

        // cria aluguel
        RentalEntity rental = new RentalEntity();
        rental.setCustomer(customer);
        rental.setRentedAt(Instant.now());
        rental.setDueAt(dto.dueAt());
        rental.setStatus(RentalStatus.OPEN);

        double total = 0.0;

        for (var movie : movies) {
            // marca como indisponível
            movie.setAvailable(false);

            RentalItemEntity item = new RentalItemEntity();
            item.setMovie(movie);
            item.setItemPrice(movie.getPrice());

            rental.addItem(item);

            total += movie.getPrice();
        }

        rental.setTotalPrice(total);

        // salva (cascade salva itens)
        var saved = rentalRepository.save(rental);
        return rentalMapper.toDetailDTO(saved);
    }

    public List<RentalDetailDTO> findAll() {
        return rentalRepository.findAll().stream()
                .map(rentalMapper::toDetailDTO)
                .toList();
    }

    public RentalDetailDTO findById(Long id) throws BusinessException {
        var rental = rentalRepository.findById(id)
                .orElseThrow(() -> new BusinessException(messages.getMessage("aluguel.nao.encontrado")));
        return rentalMapper.toDetailDTO(rental);
    }

    @Transactional
    public void returnRental(Long rentalId, RentalReturnDTO dto) throws BusinessException {
        var rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new BusinessException(messages.getMessage("aluguel.nao.encontrado")));

        if (rental.getStatus() != RentalStatus.OPEN) {
            throw new BusinessException(messages.getMessage("aluguel.nao.aberto"));
        }

        rental.setReturnedAt(dto.returnedAt());
        rental.setStatus(RentalStatus.RETURNED);

        // libera filmes
        for (var item : rental.getItems()) {
            var movie = item.getMovie();
            movie.setAvailable(true);
        }

        rentalRepository.save(rental);
    }

    @Transactional
    public void returnRentalItem(Long rentalId, Long itemId, RentalItemReturnDTO dto) throws BusinessException {
        var rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new BusinessException(messages.getMessage("aluguel.nao.encontrado")));

        if (rental.getStatus() != RentalStatus.OPEN) {
            throw new BusinessException(messages.getMessage("aluguel.nao.aberto"));
        }

        RentalItemEntity item = rental.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(messages.getMessage("item.aluguel.nao.encontrado")));

        if (item.getReturnedAt() != null) {
            throw new BusinessException(messages.getMessage("item.ja.devolvido"));
        }

        item.setReturnedAt(dto.returnedAt());

        // libera o filme no estoque
        var movie = item.getMovie();
        movie.setAvailable(true);

        // se todos os itens devolvidos, fecha o aluguel
        boolean allReturned = rental.getItems().stream().allMatch(i -> i.getReturnedAt() != null);
        if (allReturned) {
            rental.setReturnedAt(dto.returnedAt());
            rental.setStatus(RentalStatus.RETURNED);
        }

        rentalRepository.save(rental);
    }
}
