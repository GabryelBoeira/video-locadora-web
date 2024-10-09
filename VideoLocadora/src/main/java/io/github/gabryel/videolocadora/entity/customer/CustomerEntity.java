package io.github.gabryel.videolocadora.entity.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 2106833642965188722L;

    @Id
    private Long id;
    private String name;
    private String cpf;
    private boolean delayDevolution;
    private String email;

    @OneToMany
    private List<AddressEntity> addressList;

    public CustomerEntity() {}

}
