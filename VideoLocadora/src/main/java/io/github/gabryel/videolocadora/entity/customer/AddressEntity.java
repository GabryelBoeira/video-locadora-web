package io.github.gabryel.videolocadora.entity.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "address")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 198873988892602989L;

    @Id
    private String id;

    private String street;

    private String number;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private boolean active;

    private boolean isBillingAddress;

    @ManyToOne
    private CustomerEntity customer;

    public AddressEntity() {
        this.isBillingAddress = false;
        this.active = true;
    }

}
