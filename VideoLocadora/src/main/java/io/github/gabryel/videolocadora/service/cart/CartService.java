package io.github.gabryel.videolocadora.service.cart;

import io.github.gabryel.videolocadora.configuration.Messages;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.cart.*;
import io.github.gabryel.videolocadora.model.entity.*;
import io.github.gabryel.videolocadora.model.entity.cart.CartEntity;
import io.github.gabryel.videolocadora.model.entity.cart.CartItemEntity;
import io.github.gabryel.videolocadora.model.enums.CartStatus;
import io.github.gabryel.videolocadora.model.mapper.cart.CartMapper;
import io.github.gabryel.videolocadora.repository.*;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final MovieRepository movieRepository;
    private final CartMapper cartMapper;
    private final Messages messages;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            CustomerRepository customerRepository,
            MovieRepository movieRepository,
            CartMapper cartMapper,
            Messages messages
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.movieRepository = movieRepository;
        this.cartMapper = cartMapper;
        this.messages = messages;
    }

    /**
     * Cria um novo carrinho
     */
    @Transactional
    public CartDetailDTO createCart(CartCreateDTO dto) throws BusinessException {
        CartEntity cart = new CartEntity();

        if (dto.customerId() != null) {
            var customer = customerRepository.findById(dto.customerId())
                    .orElseThrow(() -> new BusinessException(messages.getMessage("cliente.nao.encontrado")));
            cart.setCustomer(customer);
        }

        cart.setExpiresAt(Instant.now().plus(dto.expirationMinutes(), ChronoUnit.MINUTES));
        cart.setStatus(CartStatus.ACTIVE);

        var saved = cartRepository.save(cart);
        return cartMapper.toDetailDTO(saved);
    }

    /**
     * Adiciona item ao carrinho de forma assíncrona
     */
    @Async
    @Transactional
    public CompletableFuture<CartDetailDTO> addItemAsync(String sessionId, CartAddItemDTO dto) throws BusinessException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return addItem(sessionId, dto);
            } catch (BusinessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Adiciona item ao carrinho (síncrono)
     */
    @Transactional
    public CartDetailDTO addItem(String sessionId, CartAddItemDTO dto) throws BusinessException {
        // 1. Buscar carrinho
        CartEntity cart = cartRepository.findBySessionIdAndStatus(sessionId, CartStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(messages.getMessage("carrinho.nao.encontrado")));

        // 2. Validar expiração
        if (cart.isExpired()) {
            cart.setStatus(CartStatus.EXPIRED);
            cartRepository.save(cart);
            throw new BusinessException(messages.getMessage("carrinho.expirado"));
        }

        // 3. Buscar filme
        MovieEntity movie = movieRepository.findById(dto.movieId())
                .orElseThrow(() -> new BusinessException(messages.getMessage("filme.nao.encontrado")));

        // 4. Verificar disponibilidade
        if (!movie.getAvailable()) {
            throw new BusinessException(messages.getMessage("filme.indisponivel"));
        }

        // 5. Verificar se já existe no carrinho
        boolean alreadyInCart = cart.getItems().stream()
                .anyMatch(item -> item.getMovie().getId().equals(dto.movieId()));

        if (alreadyInCart) {
            throw new BusinessException(messages.getMessage("filme.ja.no.carrinho"));
        }

        // 6. Criar item com reserva temporária
        CartItemEntity item = new CartItemEntity();
        item.setMovie(movie);
        item.setRentalDays(dto.rentalDays());
        item.setUnitPrice(movie.getPrice());
        item.setReservationExpiresAt(cart.getExpiresAt());
        item.setReserved(true);

        // 7. Marcar filme como temporariamente indisponível
        movie.setAvailable(false);

        // 8. Adicionar ao carrinho
        cart.addItem(item);

        var saved = cartRepository.save(cart);
        return cartMapper.toDetailDTO(saved);
    }

    /**
     * Remove item do carrinho
     */
    @Transactional
    public CartDetailDTO removeItem(String sessionId, Long itemId) throws BusinessException {
        CartEntity cart = cartRepository.findBySessionIdAndStatus(sessionId, CartStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(messages.getMessage("carrinho.nao.encontrado")));

        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(messages.getMessage("item.nao.encontrado")));

        // Libera o filme
        MovieEntity movie = item.getMovie();
        movie.setAvailable(true);

        cart.removeItem(item);

        var saved = cartRepository.save(cart);
        return cartMapper.toDetailDTO(saved);
    }

    /**
     * Busca carrinho por sessionId
     */
    public CartDetailDTO findBySessionId(String sessionId) throws BusinessException {
        CartEntity cart = cartRepository.findBySessionIdAndStatus(sessionId, CartStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(messages.getMessage("carrinho.nao.encontrado")));

        return cartMapper.toDetailDTO(cart);
    }

    /**
     * Job agendado: expira carrinhos e libera estoque
     * Executa a cada 5 minutos
     */
    @Scheduled(fixedDelay = 300000) // 5 minutos
    @Transactional
    public void expireAbandonedCarts() {
        List<CartEntity> expiredCarts = cartRepository.findExpiredCarts(Instant.now());

        expiredCarts.forEach(cart -> {
            cart.setStatus(CartStatus.EXPIRED);

            // Libera filmes reservados
            cart.getItems().forEach(item -> {
                MovieEntity movie = item.getMovie();
                movie.setAvailable(true);
                item.setReserved(false);
            });

            cartRepository.save(cart);
        });
    }

    /**
     * Converte carrinho em locação
     */
    @Transactional
    public Long convertToRental(String sessionId) throws BusinessException {
        CartEntity cart = cartRepository.findBySessionIdAndStatus(sessionId, CartStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(messages.getMessage("carrinho.nao.encontrado")));

        if (cart.getCustomer() == null) {
            throw new BusinessException(messages.getMessage("carrinho.sem.cliente"));
        }

        if (cart.getItems().isEmpty()) {
            throw new BusinessException(messages.getMessage("carrinho.vazio"));
        }

        // Cria a locação (você adaptaria para usar RentalService)
        RentalEntity rental = new RentalEntity();
        rental.setCustomer(cart.getCustomer());
        rental.setRentedAt(Instant.now());
        rental.setDueAt(Instant.now().plus(7, ChronoUnit.DAYS)); // exemplo
        rental.setStatus(io.github.gabryel.videolocadora.model.enums.RentalStatus.OPEN);
        rental.setTotalPrice(cart.getTotalPrice());

        cart.getItems().forEach(cartItem -> {
            RentalItemEntity rentalItem = new RentalItemEntity();
            rentalItem.setMovie(cartItem.getMovie());
            rentalItem.setItemPrice(cartItem.getSubtotal());
            rental.addItem(rentalItem);
        });

        // Marca carrinho como convertido
        cart.setStatus(CartStatus.CONVERTED_TO_RENTAL);

        // Salva (adapte para usar seu RentalRepository)
        // Long rentalId = rentalRepository.save(rental).getId();

        cartRepository.save(cart);

        return 1L; // retornaria rentalId
    }
}
