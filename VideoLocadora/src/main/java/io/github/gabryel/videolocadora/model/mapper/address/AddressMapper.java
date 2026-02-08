package io.github.gabryel.videolocadora.model.mapper.address;

import io.github.gabryel.videolocadora.model.dto.address.AddressDetailDTO;
import io.github.gabryel.videolocadora.model.dto.address.AddressSaveDTO;
import io.github.gabryel.videolocadora.model.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "primaryAddress", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "zipCode", qualifiedByName = "removeFormatZipCode")
    AddressEntity toEntity(AddressSaveDTO addressSaveDTO);

    List<AddressEntity> toEntityList(List<AddressSaveDTO> addressSaveDTOList);

    @Mapping(target = "zipCode", qualifiedByName = "addFormatZipCode")
    AddressDetailDTO toDetailDTO(AddressEntity addressEntity);

    List<AddressDetailDTO> toDetailDTOList(List<AddressEntity> addressEntityList);

    @Named("removeFormatZipCode")
    default String removeFormatZipCode(String zipCode) {
        return zipCode.replace(".", "").replace("-", "");
    }

    @Named("addFormatZipCode")
    default String addFormatZipCode(String zipCode) {
        return String.format("%s-%s", zipCode.substring(0, 5), zipCode.substring(5, 8));
    }

}
