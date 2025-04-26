package io.github.gabryel.videolocadora.repository.customer;

import io.github.gabryel.videolocadora.entity.CustomerEntity;
import org.springframework.data.jpa.domain.Specification;

public abstract class CustomerSpecs {

    public static Specification<CustomerEntity> nameLike(String name) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("name")), "%" + name + "%".toUpperCase());
    }

    public static Specification<CustomerEntity> emailLike(String email) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("email")), "%" + email + "%".toUpperCase());
    }

}
