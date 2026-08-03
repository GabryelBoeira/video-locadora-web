package io.github.gabryel.videolocadora.model.mapper.rent;

import io.github.gabryel.videolocadora.model.dto.rental.RentalDetailDTO;
import io.github.gabryel.videolocadora.model.entity.RentalEntity;
import io.github.gabryel.videolocadora.model.entity.RentalItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "items", source = "items")
    RentalDetailDTO toDetailDTO(RentalEntity entity);

    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "movieTitle", source = "movie.title")
    RentalDetailDTO.RentalItemDetailDTO toItemDetailDTO(RentalItemEntity entity);

    List<RentalDetailDTO.RentalItemDetailDTO> toItemDetailDTOList(List<RentalItemEntity> items);

}
