package io.github.gabryel.videolocadora.entity.customer;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "address")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 198873988892602989L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private Integer number;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    private boolean isActive;

    private boolean isPrimaryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    public AddressEntity() {}

    public AddressEntity(String street, Integer number, String city, String state, String country, String zipCode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.isActive = true;
        this.isPrimaryAddress = false;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
        if (customer != null && !customer.getAddresses().contains(this)) {
            customer.getAddresses().add(this);
        }
    }
}
