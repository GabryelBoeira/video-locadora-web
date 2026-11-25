package io.github.gabryel.bff.service;

import io.github.gabryel.bff.client.CustomerClient;
import io.github.gabryel.bff.dto.customer.CustomerCreateRequestDTO;
import io.github.gabryel.bff.dto.customer.CustomerSummaryDTO;
import io.github.gabryel.bff.dto.customer.CustomerUpdateRequestDTO;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerBffService {

    private final CustomerClient customerClient;

    public CustomerBffService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public PageResultDTO<CustomerSummaryDTO> findAll(Pageable pageable) {
        return customerClient.findAll(pageable);
    }

    public CustomerSummaryDTO findById(Long id) {
        return customerClient.findById(id);
    }

    public CustomerSummaryDTO findByCpf(String cpf) {
        return customerClient.findByCpf(cpf);
    }

    public void create(CustomerCreateRequestDTO request) {
        customerClient.create(request);
    }

    public CustomerSummaryDTO update(Long id, CustomerUpdateRequestDTO request) {
        return customerClient.update(id, request);
    }

    public void delete(Long id) {
        customerClient.delete(id);
    }

}
