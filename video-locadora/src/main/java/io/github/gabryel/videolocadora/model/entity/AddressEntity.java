package io.github.gabryel.videolocadora.model.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "address")
public class AddressEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 198873988892602989L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private Integer number;

    private String city;

    private String state;

    private String country;

    private String neighborhood;

    private String complement;

    private boolean active;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "primary_address")
    private boolean primaryAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    public AddressEntity() {}

    public AddressEntity(String street, Integer number, String city, String state, String country, String zipCode, String neighborhood, String complement) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.active = true;
        this.primaryAddress = false;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
        if (customer != null && !customer.getAddresses().contains(this)) {
            customer.getAddresses().add(this);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(boolean primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }
}
