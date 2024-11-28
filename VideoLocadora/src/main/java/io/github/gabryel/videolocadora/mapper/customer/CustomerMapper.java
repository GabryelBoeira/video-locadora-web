package io.github.gabryel.videolocadora.mapper.customer;

import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.entity.customer.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDetailDTO toDetailDTO(CustomerEntity customerEntity);

}
