package io.github.gabryel.videolocadora.service;


import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.mapper.address.AddressMapper;
import io.github.gabryel.videolocadora.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private CustomerService customerService;

    private CustomerMapper customerMapper;
    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        customerMapper = Mappers.getMapper(CustomerMapper.class);
        addressMapper = Mappers.getMapper(AddressMapper.class);
    }

    @Test
    void save_shouldSaveCustomer() {
        CustomerSaveDTO saveDTO = new CustomerSaveDTO("Name", "12345678900", null, null);

        customerService.save(saveDTO);

        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void findById_shouldReturnCustomerDetailDTO_whenCustomerExists() throws BusinessException {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerMapper.toEntity(new CustomerSaveDTO("Name", "12345678900", null, null))));

        CustomerDetailDTO result = customerService.findById(id);

        assertNotNull(result);
    }

    @Test
    void findById_shouldThrowBusinessException_whenCustomerDoesNotExist() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> customerService.findById(id));
    }

    @Test
    void findAllPaginated_shouldReturnPagedResponseDTO_withContent() {
        Pageable pageable = PageRequest.of(0, 10);
        when(customerRepository.findAll(pageable)).thenReturn(Page.empty());
        PagedResponseDTO<CustomerDetailDTO> result = customerService.findAllPaginated(PageRequest.of(0, 10));

        assertNotNull(result);
        assertNull(result.content(), "O conteúdo deve ser nulo"); // Verifica se o conteúdo foi mapeado corretamente
    }

    @Test
    void delete_shouldDeleteCustomer() {
        Long id = 1L;

        customerService.delete(id);

        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void findByCpf_shouldReturnCustomerDetailDTO_whenCpfExists() throws BusinessException {
        String cpf = "12345678900";
        when(customerRepository.findByCpfEquals(cpf)).thenReturn(Optional.of(customerMapper.toEntity(new CustomerSaveDTO("Name", cpf, null, null))));

        CustomerDetailDTO result = customerService.findByCpf(cpf);

        assertNotNull(result);
    }

    @Test
    void findByCpf_shouldThrowBusinessException_whenCpfDoesNotExist() {
        String cpf = "12345678900";
        when(customerRepository.findByCpfEquals(cpf)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> customerService.findByCpf(cpf));
    }

    @Test
    void update_shouldUpdateCustomer() throws BusinessException {
        Long id = 1L;
        CustomerSaveDTO updateDTO = new CustomerSaveDTO("Updated Name", "98765432100", null, null);

        customerService.update(id, updateDTO);

        verify(customerRepository, times(1)).save(any());
    }
}