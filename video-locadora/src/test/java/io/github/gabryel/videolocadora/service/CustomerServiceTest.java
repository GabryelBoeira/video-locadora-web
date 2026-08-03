package io.github.gabryel.videolocadora.service;

import io.github.gabryel.videolocadora.configuration.Messages;
import io.github.gabryel.videolocadora.exception.CustomerException;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.model.dto.customer.CustomerUpdateDTO;
import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.entity.CustomerEntity;
import io.github.gabryel.videolocadora.model.mapper.address.AddressMapper;
import io.github.gabryel.videolocadora.model.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import io.github.gabryel.videolocadora.util.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private ObjectUtils utils;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private Messages messages;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CustomerMapper customerMapper;

    @Test
    void save_ShouldPersistCustomerWithFullData() throws CustomerException {
        CustomerSaveDTO dto = utils.customerSaveDTO();
        CustomerEntity customerEntity = utils.customerEntity();
        when(customerRepository.findByCpfEquals(dto.cpf())).thenReturn(Optional.empty());
        when(customerMapper.toEntity(dto)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);

        customerService.save(dto);

        verify(customerRepository).save(customerEntity);
        verify(customerMapper).toEntity(dto);
    }

    @Test
    void save_ShouldThrowException_WhenCpfAlreadyExists() {
        CustomerSaveDTO dto = utils.customerSaveDTO();
        CustomerEntity customerEntity = utils.customerEntity();
        when(customerRepository.findByCpfEquals(dto.cpf())).thenReturn(Optional.of(customerEntity));
        when(messages.getMessage("cliente.cpf.duplicado")).thenReturn("CPF duplicado");

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.save(dto));
        assertEquals("CPF duplicado", exception.getMessage());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void findById_ShouldReturnCustomerDetailDTO_WhenIdExists() throws CustomerException {
        Long id = 1L;
        CustomerEntity entity = utils.customerEntity();
        CustomerDetailDTO detailDTO = utils.customerDetailDTO();
        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));
        when(customerMapper.toDetailDTO(entity)).thenReturn(detailDTO);

        CustomerDetailDTO result = customerService.findById(id);

        assertNotNull(result);
        assertEquals(detailDTO, result);
    }

    @Test
    void findById_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(messages.getMessage("cliente.nao.encontrado.id")).thenReturn("Cliente não encontrado");

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.findById(id));
        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void findAllPaginated_ShouldReturnPagedResponseDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        CustomerEntity entity = utils.customerEntity();
        Page<CustomerEntity> page = new PageImpl<>(Collections.singletonList(entity));
        PagedResponseDTO<CustomerDetailDTO> pagedResponseDTO = new PagedResponseDTO<>(
                Collections.singletonList(utils.customerDetailDTO()), 1, 10, 1, 1, true, true
        );

        when(customerRepository.findAll(pageable)).thenReturn(page);
        when(customerMapper.toDetailPage(page)).thenReturn(pagedResponseDTO);

        PagedResponseDTO<CustomerDetailDTO> result = customerService.findAllPaginated(pageable);

        assertNotNull(result);
        assertEquals(pagedResponseDTO, result);
    }

    @Test
    void softDelete_ShouldDisableCustomer_WhenIdExists() throws CustomerException {
        Long id = 1L;
        CustomerEntity entity = utils.customerEntity();
        entity.setEnable(true);
        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));

        customerService.softDelete(id);

        assertFalse(entity.getEnable());
        verify(customerRepository).save(entity);
    }

    @Test
    void softDelete_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(messages.getMessage("cliente.nao.encontrado.id")).thenReturn("Cliente não encontrado");

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.softDelete(id));
        assertEquals("Cliente não encontrado", exception.getMessage());
    }

    @Test
    void findByCpf_ShouldReturnCustomerDetailDTO_WhenCpfExists() throws CustomerException {
        String cpf = "12345678909";
        CustomerEntity entity = utils.customerEntity();
        CustomerDetailDTO detailDTO = utils.customerDetailDTO();
        when(customerRepository.findByCpfEquals(cpf)).thenReturn(Optional.of(entity));
        when(customerMapper.toDetailDTO(entity)).thenReturn(detailDTO);

        CustomerDetailDTO result = customerService.findByCpf(cpf);

        assertNotNull(result);
        assertEquals(detailDTO, result);
    }

    @Test
    void findByCpf_ShouldThrowException_WhenCpfDoesNotExist() {
        String cpf = "12345678909";
        when(customerRepository.findByCpfEquals(cpf)).thenReturn(Optional.empty());
        when(messages.getMessage("cliente.nao.encontrado.cpf")).thenReturn("CPF não encontrado");

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.findByCpf(cpf));
        assertEquals("CPF não encontrado", exception.getMessage());
    }

    @Test
    void update_ShouldUpdateCustomer_WhenIdExists() throws CustomerException {
        Long id = 1L;
        CustomerUpdateDTO updateDTO = utils.customerUpdateDTO();
        CustomerEntity entity = utils.customerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));
        when(customerMapper.updateEntityFromDto(updateDTO, entity)).thenReturn(entity);

        customerService.update(id, updateDTO);

        verify(customerRepository).save(entity);
        verify(customerMapper).updateEntityFromDto(updateDTO, entity);
    }

    @Test
    void update_ShouldThrowException_WhenIdDoesNotExist() {
        Long id = 1L;
        CustomerUpdateDTO updateDTO = utils.customerUpdateDTO();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(messages.getMessage("cliente.nao.encontrado.id")).thenReturn("Cliente não encontrado");

        CustomerException exception = assertThrows(CustomerException.class, () -> customerService.update(id, updateDTO));
        assertEquals("Cliente não encontrado", exception.getMessage());
    }

}