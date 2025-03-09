package io.github.gabryel.videolocadora.mapper.customer;

import io.github.gabryel.videolocadora.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.entity.customer.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;


@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDetailDTO toDetailDTO(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "delayDevolution", ignore = true)
    CustomerEntity toEntity(CustomerSaveDTO customerSaveDTO);

    /**
     * Maps a page of customer entities to a page of customer detail DTOs.
     *
     * @param customerPage the page of customer entities to be mapped
     * @return a page with the customer detail DTOs
     */
    default PagedResponseDTO<CustomerDetailDTO> toDetailPage(Page<CustomerEntity> customerPage) {
        List<CustomerDetailDTO> customerDetailDTOList = customerPage.getContent().stream()
                .map(this::toDetailDTO)
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(
                customerDetailDTOList,
                customerPage.getPageable().getPageNumber(),
                customerPage.getPageable().getPageSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.isFirst(),
                customerPage.isLast()
        );
    }

}
