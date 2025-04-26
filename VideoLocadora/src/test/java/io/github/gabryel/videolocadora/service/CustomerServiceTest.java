package io.github.gabryel.videolocadora.service;

import io.github.gabryel.videolocadora.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.entity.CustomerEntity;
import io.github.gabryel.videolocadora.mapper.address.AddressMapper;
import io.github.gabryel.videolocadora.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.repository.customer.CustomerRepository;
import io.github.gabryel.videolocadora.service.customer.CustomerService;
import io.github.gabryel.videolocadora.util.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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