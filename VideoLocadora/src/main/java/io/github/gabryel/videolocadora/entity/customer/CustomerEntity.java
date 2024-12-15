package io.github.gabryel.videolocadora.entity.customer;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 2106833642965188722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cpf;

    private String email;

    @Column(name = "delay_devolution")
    private boolean delayDevolution = false;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
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
}
