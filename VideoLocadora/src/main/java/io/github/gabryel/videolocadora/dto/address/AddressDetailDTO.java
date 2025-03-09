package io.github.gabryel.videolocadora.dto.address;

import lombok.Data;


@Data
public class AddressDetailDTO {

    private Long id;

    private String street;

    private Integer number;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private boolean isActive;

    private boolean isPrimaryAddress;

}
