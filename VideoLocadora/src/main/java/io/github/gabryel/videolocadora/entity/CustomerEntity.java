package io.github.gabryel.videolocadora.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2106833642965188722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String email;

    @Column(name = "delay_devolution")
    private boolean delayDevolution = false;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses = new ArrayList<>();

    public CustomerEntity() {}

    public CustomerEntity(String name, String cpf, String email) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.addresses = new ArrayList<>();
    }

    public void addAddress(AddressEntity address) {
        this.addresses.add(address);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDelayDevolution() {
        return delayDevolution;
    }

    public void setDelayDevolution(boolean delayDevolution) {
        this.delayDevolution = delayDevolution;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

}
