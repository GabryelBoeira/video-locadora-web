package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.CustomerApi;
import io.github.gabryel.videolocadora.exception.CustomerException;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerUpdateDTO;
import io.github.gabryel.videolocadora.model.dto.hateoas.Link;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.dto.hateoas.ResourceCollection;
import io.github.gabryel.videolocadora.model.mapper.hateoas.LinkBuilder;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<ResourceCollection<CustomerDetailDTO>> getAll(Pageable pageable) {

        return ResponseEntity.ok(ResourceCollection.of(customerService.findAllPaginated(pageable), "/customer", this::customerLinks));
    }

    @Override
    public ResponseEntity<Resource<CustomerDetailDTO>> getById(Long id) throws CustomerException {
        var customer = customerService.findById(id);
        return ResponseEntity.ok(toResource(customer));
    }

    @Override
    public ResponseEntity<Void> createNewCustomer(@Valid @RequestBody CustomerSaveDTO createDto) throws CustomerException {
        var customerId = customerService.save(createDto);
        return ResponseEntity
                .created(URI.create("/customer/" + customerId))
                .build();
    }

    @Override
    public ResponseEntity<Resource<CustomerDetailDTO>> updateCustomer(Long id, @Valid @RequestBody CustomerUpdateDTO updateDto) throws CustomerException {
        var updatedCustomer = customerService.update(id, updateDto);
        return ResponseEntity.ok(toResource(updatedCustomer));
    }

    @Override
    public ResponseEntity<Resource<CustomerDetailDTO>> getCustomerByCpf(String cpf) throws CustomerException {
        var customer = customerService.findByCpf(cpf);
        return ResponseEntity.ok(toResource(customer));
    }

    @Override
    public ResponseEntity<Void> desactivateCustomer(Long id) throws CustomerException {
        customerService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    private Resource<CustomerDetailDTO> toResource(CustomerDetailDTO customer) {
        return Resource.of(customer, customerLinks(customer));
    }

    private Map<String, Link> customerLinks(CustomerDetailDTO customer) {
        var links = LinkBuilder.from("/customer", customer.id())
                .self()
                .put()
                .custom("desactivate", "", "DELETE")
                .build();

        links.putAll(LinkBuilder.from("/customer")
                .custom("byCpf", "/cpf/" + customer.cpf(), "GET")
                .build());

        return links;
    }

}
