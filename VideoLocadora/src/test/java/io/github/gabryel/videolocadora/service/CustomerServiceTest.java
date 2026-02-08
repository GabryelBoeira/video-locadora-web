package io.github.gabryel.videolocadora.service;

import io.github.gabryel.videolocadora.model.dto.customer.CustomerSaveDTO;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private ObjectUtils utils;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CustomerMapper customerMapper;

    @Test
    void save_ShouldPersistCustomerWithFullData() {
        CustomerSaveDTO dto = utils.customerSaveDTO();
        CustomerEntity customerEntity = utils.customerEntity();
        when(customerMapper.toEntity(dto)).thenReturn(customerEntity);
        when(customerRepository.save(customerEntity)).thenReturn(customerEntity);

        customerService.save(dto);

        verify(customerRepository).save(customerEntity);
        verify(customerMapper).toEntity(dto);
    }

}