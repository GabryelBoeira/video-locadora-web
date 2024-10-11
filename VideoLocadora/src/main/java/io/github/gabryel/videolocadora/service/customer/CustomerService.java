package io.github.gabryel.videolocadora.service.customer;

import io.github.gabryel.videolocadora.entity.customer.CustomerEntity;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

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
    public CustomerEntity findById(Long id) throws BusinessException {
        return customerRepository.findById(id).orElseThrow(() -> new BusinessException("Customer not found"));
    }

    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

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
