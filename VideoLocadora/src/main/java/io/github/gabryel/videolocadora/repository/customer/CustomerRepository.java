package io.github.gabryel.videolocadora.repository.customer;

import io.github.gabryel.videolocadora.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, JpaSpecificationExecutor<CustomerEntity> {

    Optional<CustomerEntity> findByCpfEquals(String cpf);
}
