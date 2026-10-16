package io.github.gabryel.videolocadora.model.mapper.rent;

import io.github.gabryel.videolocadora.model.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalDetailDTO;
import io.github.gabryel.videolocadora.model.entity.RentalEntity;
import io.github.gabryel.videolocadora.model.entity.RentalItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

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

    /**
     * Maps a page of rental entities to a page of rental detail DTOs.
     *
     * @param rentalPage the page of rental entities to be mapped
     * @return a page with the rental detail DTOs
     */
    default PagedResponseDTO<RentalDetailDTO> toDetailPage(Page<RentalEntity> rentalPage) {
        return PagedResponseDTO.fromPage(rentalPage, this::toDetailDTO);
    }

}
