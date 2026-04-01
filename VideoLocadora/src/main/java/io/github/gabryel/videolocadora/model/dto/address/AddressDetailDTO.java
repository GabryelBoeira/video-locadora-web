package io.github.gabryel.videolocadora.model.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto contendo os detalhes do endereço.")
public record AddressDetailDTO(
    Long id,
    String street,
    Integer number,
    String city,
    String state,
    String country,
    String zipCode,
    String neighborhood,
    String complement,
    boolean active,
    boolean primaryAddress
) {

}
