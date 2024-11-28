package io.github.gabryel.videolocadora.service.customer;

import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.entity.customer.CustomerEntity;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void save(CustomerEntity customer) {
        customerRepository.save(customer);
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
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
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
    public List<CustomerEntity> findByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }

}
