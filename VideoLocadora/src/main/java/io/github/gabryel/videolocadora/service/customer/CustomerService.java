package io.github.gabryel.videolocadora.service.customer;

import io.github.gabryel.videolocadora.entity.customer.CustomerEntity;
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

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerEntity> findByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }

}
