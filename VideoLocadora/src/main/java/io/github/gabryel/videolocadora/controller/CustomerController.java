package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.CustomerApi;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerUpdateDTO;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.exception.CustomerException;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController implements CustomerApi {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ResponseEntity<PagedResponseDTO<CustomerDetailDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(customerService.findAllPaginated(pageable));
    }

    @Override
    public ResponseEntity<CustomerDetailDTO> getById(Long id) throws CustomerException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @Override
    public ResponseEntity<Void> createNewCustomer(@Valid @RequestBody CustomerSaveDTO createDto) throws CustomerException {
        customerService.save(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> updateCustomer(Long id, @Valid @RequestBody CustomerUpdateDTO updateDto) throws CustomerException {
        customerService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CustomerDetailDTO> getCustomerByCpf(String cpf) throws CustomerException {
        return ResponseEntity.ok(customerService.findByCpf(cpf));
    }

    @Override
    public ResponseEntity<Void> desactivateCustomer(Long id) throws CustomerException {
        customerService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

}
