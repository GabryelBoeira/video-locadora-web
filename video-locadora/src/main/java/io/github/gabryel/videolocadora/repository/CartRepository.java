package io.github.gabryel.videolocadora.repository;

import io.github.gabryel.videolocadora.model.entity.cart.CartEntity;
import io.github.gabryel.videolocadora.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findBySessionIdAndStatus(String sessionId, CartStatus status);

    @Query("SELECT c FROM CartEntity c WHERE c.status = 'ACTIVE' AND c.expiresAt < :now")
    List<CartEntity> findExpiredCarts(@Param("now") Instant now);

    Optional<CartEntity> findByCustomerIdAndStatus(Long customerId, CartStatus status);
}
