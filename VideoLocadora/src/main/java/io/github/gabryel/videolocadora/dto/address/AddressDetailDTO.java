package io.github.gabryel.videolocadora.dto.address;

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

    public AddressDetailDTO() {
        this(null, null, null, null, null, null, null, null, null, false, false);
    }

}
