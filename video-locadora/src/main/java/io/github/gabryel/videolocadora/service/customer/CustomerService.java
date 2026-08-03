package io.github.gabryel.videolocadora.service.customer;

import io.github.gabryel.videolocadora.configuration.Messages;
import io.github.gabryel.videolocadora.event.producer.EventPublisher;
import io.github.gabryel.videolocadora.exception.CustomerException;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerUpdateDTO;
import io.github.gabryel.videolocadora.model.dto.event.PayloadEvent;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.entity.CustomerEntity;
import io.github.gabryel.videolocadora.model.enums.EventTypeEnum;
import io.github.gabryel.videolocadora.model.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static io.github.gabryel.videolocadora.model.enums.EventTypeEnum.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final Messages messages;
    private final EventPublisher eventPublisher;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, Messages messages, EventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.messages = messages;
        this.eventPublisher = eventPublisher;
    }

    public ResponseEntity<Resource<PagedResponseDTO<Resource<CustomerDetailDTO>>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(customerMapper.toPagedResponseDTO(customerRepository.findAll(pageable)));
    }

    /**
     * Saves a customer.
     *
     * @param createDto the customer to be saved
     */
    public Long save(CustomerSaveDTO createDto) throws CustomerException {
        if (customerRepository.findByCpfEquals(createDto.cpf()).isPresent())
            throw new CustomerException(messages.getMessage("cliente.cpf.duplicado"));

        CustomerEntity entity = customerRepository.save(customerMapper.toEntity(createDto));

        //Enviar notificação
        publish(CUSTOMER_CREATED, entity);
        return entity.getId();
    }

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be found
     * @return the customer found
     * @throws CustomerException if the customer is not found
     */
    public CustomerDetailDTO findById(Long id) throws CustomerException {
        var entity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(messages.getMessage("cliente.nao.encontrado.id")));
        return customerMapper.toDetailDTO(entity);
    }

    /**
     * Retrieves all customers paginated.
     *
     * @return a list of all customers
     */
    public PagedResponseDTO<CustomerDetailDTO> findAllPaginated(Pageable pageable) {
        var result = customerRepository.findAll(pageable);
        return customerMapper.toDetailPage(result);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to be deleted
     */
    public void softDelete(Long id) throws CustomerException {
        var entity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(messages.getMessage("cliente.nao.encontrado.id")));
        entity.setEnable(false);

        //Enviar notificação
        publish(CUSTOMER_DELETED, entity);

        customerRepository.save(entity);
    }

    /**
     * Finds a customer by CPF.
     *
     * @param cpf the customer's CPF
     * @return a list containing the customer found
     */
    public CustomerDetailDTO findByCpf(String cpf) throws CustomerException {
        var entity = customerRepository.findByCpfEquals(cpf)
                .orElseThrow(() -> new CustomerException(messages.getMessage("cliente.nao.encontrado.cpf")));

        return customerMapper.toDetailDTO(entity);
    }

    /**
     * Updates a customer.
     *
     * @param id                the ID of the customer to be updated
     * @param customerUpdateDTO the customer to be updated
     */
    public CustomerDetailDTO update(final Long id, CustomerUpdateDTO customerUpdateDTO) throws CustomerException {
        var entity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(messages.getMessage("cliente.nao.encontrado.id")));

        //Enviar notificação
        publish(CUSTOMER_UPDATED, entity);
        return customerMapper.toDetailDTO(customerRepository.save(customerMapper.updateEntityFromDto(customerUpdateDTO, entity)));
    }

    private void publish(final EventTypeEnum type, CustomerEntity entity) {
        PayloadEvent event = PayloadEvent.builder()
                .customerName(entity.getName())
                .customerEmail(entity.getEmail())
                .cellPhone(entity.getCellPhone())
                .build();

        eventPublisher.publish(type, event);
    }

}
