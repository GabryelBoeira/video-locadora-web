package io.github.gabryel.videolocadora.service.customer;

import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Saves a customer.
     *
     * @param createDto the customer to be saved
     */
    public void save(CustomerSaveDTO createDto) {
        customerRepository.save(customerMapper.toEntity(createDto));
    }

    /**
     * Finds a customer by ID.
     *
     * @param id the ID of the customer to be found
     * @return the customer found
     * @throws BusinessException if the customer is not found
     */
    public CustomerDetailDTO findById(Long id) throws BusinessException {
        var entity = customerRepository.findById(id).orElseThrow(() -> new BusinessException("Customer not found"));
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
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    /**
     * Finds a customer by CPF.
     *
     * @param cpf the customer's CPF
     * @return a list containing the customer found
     */
    public CustomerDetailDTO findByCpf(String cpf) throws BusinessException {
        var entity = customerRepository.findByCpfEquals(cpf).orElseThrow(() -> new BusinessException("CPF not found"));

        return customerMapper.toDetailDTO(entity);
    }

    /**
     * Updates a customer.
     *
     * @param id                the ID of the customer to be updated
     * @param customerUpdateDTO the customer to be updated
     */
    public void update(Long id, CustomerSaveDTO customerUpdateDTO) throws BusinessException {
        var entity = customerMapper.toEntity(customerUpdateDTO);
        entity.setId(id);
        customerRepository.save(entity);
    }

}
