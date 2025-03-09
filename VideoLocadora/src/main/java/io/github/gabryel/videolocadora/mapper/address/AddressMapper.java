package io.github.gabryel.videolocadora.mapper.address;

import io.github.gabryel.videolocadora.dto.address.AddressDetailDTO;
import io.github.gabryel.videolocadora.dto.address.AddressSaveDTO;
import io.github.gabryel.videolocadora.entity.customer.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "primaryAddress", ignore = true)
    @Mapping(target = "customer", ignore = true)
    AddressEntity toEntity(AddressSaveDTO addressSaveDTO);

}
