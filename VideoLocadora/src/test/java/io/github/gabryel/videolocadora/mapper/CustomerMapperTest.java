package io.github.gabryel.videolocadora.mapper;

import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.entity.CustomerEntity;
import io.github.gabryel.videolocadora.mapper.address.AddressMapper;
import io.github.gabryel.videolocadora.mapper.customer.CustomerMapper;
import io.github.gabryel.videolocadora.util.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    @InjectMocks
    private ObjectUtils utils;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class); // Inicialize com Mappers.getMapper() para o InjectMocks funcionar


    @Test
    void toEntity_ShouldConvertsToCustomerEntity() {
        CustomerSaveDTO dto = utils.customerSaveDTO();
        when(addressMapper.toEntityList(any())).thenReturn(utils.listAddressEntity());

        var entity = customerMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(entity.getName(), dto.name(), "Nome diferente do esperado");
        assertEquals(entity.getEmail(), dto.email(), "Email diferente do esperado");
        assertEquals("12345678909", entity.getCpf(), "CPF nao deveria ter mascara na entidade");
    }

    @Test
    void toEntity_ShouldRemoveCpfMask() {
        CustomerSaveDTO dtoComMascara = utils.customerSaveDTO();
        when(addressMapper.toEntityList(any())).thenReturn(utils.listAddressEntity());

        CustomerEntity entity = customerMapper.toEntity(dtoComMascara);

        assertNotNull(entity);
        Assertions.assertEquals("12345678909", entity.getCpf());
    }

    @Test
    void toEntity_ShouldConvertsToCustomerDetailDTO() {


    }

    @Test
    void toEntity_ShouldAddCpfMask() {
        CustomerEntity entity = utils.customerEntity();
        when(addressMapper.toDetailDTOList(any())).thenReturn(utils.addressDetailDTOList());

        CustomerDetailDTO detailDTO = customerMapper.toDetailDTO(entity);

        assertNotNull(detailDTO);
        assertEquals(detailDTO.name(), entity.getName(), "Nome diferente do esperado");
        assertEquals(detailDTO.email(), entity.getEmail(), "Email diferente do esperado");
        Assertions.assertEquals("123.456.789-09", detailDTO.cpf(),"CPF deve ter mascara na camada de DTO no objeto de detalhar");
    }

}
