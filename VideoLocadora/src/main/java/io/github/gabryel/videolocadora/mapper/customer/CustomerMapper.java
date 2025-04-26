package io.github.gabryel.videolocadora.mapper.customer;

import io.github.gabryel.videolocadora.dto.customer.CustomerDetailDTO;
import io.github.gabryel.videolocadora.dto.customer.CustomerSaveDTO;
import io.github.gabryel.videolocadora.dto.page.PagedResponseDTO;
import io.github.gabryel.videolocadora.entity.CustomerEntity;
import io.github.gabryel.videolocadora.mapper.address.AddressMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface CustomerMapper {

    @Mapping(source = "cpf", target = "cpf", qualifiedByName = "addFormatCpf")
    CustomerDetailDTO toDetailDTO(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "delayDevolution", ignore = true)
    @Mapping(source = "cpf", target = "cpf", qualifiedByName = "removeFormatCpf")
    CustomerEntity toEntity(CustomerSaveDTO customerSaveDTO);

    /**
     * Maps a page of customer entities to a page of customer detail DTOs.
     *
     * @param customerPage the page of customer entities to be mapped
     * @return a page with the customer detail DTOs
     */
    default PagedResponseDTO<CustomerDetailDTO> toDetailPage(Page<CustomerEntity> customerPage) {
        if (customerPage == null || customerPage.isEmpty()) {
            return new PagedResponseDTO<>(new ArrayList<>(), 0, 0, 0, 0, true, true);
        }

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

    @Named("removeFormatCpf")
    default String removeFormatCpf(String cpf) {
        return cpf.replace(".", "").replace("-", "");
    }

    @Named("addFormatCpf")
    default String addFormatCpf(String cpf) {
        return String.format("%s.%s.%s-%s", cpf.substring(0, 3), cpf.substring(3, 6), cpf.substring(6, 9), cpf.substring(9, 11));
    }

}
