package io.github.gabryel.videolocadora.mapper;

import io.github.gabryel.videolocadora.model.entity.CustomerEntity;
import io.github.gabryel.videolocadora.model.entity.RentalEntity;
import io.github.gabryel.videolocadora.model.enums.RentalStatus;
import io.github.gabryel.videolocadora.model.mapper.rent.RentalMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalMapperTest {

    private final RentalMapper rentalMapper = Mappers.getMapper(RentalMapper.class);

    @Test
    void toDetailPage_ShouldConvertPopulatedPage() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("Fulano");

        RentalEntity rental = new RentalEntity();
        rental.setCustomer(customer);
        rental.setRentedAt(Instant.now());
        rental.setDueAt(Instant.now().plusSeconds(3600));
        rental.setStatus(RentalStatus.OPEN);
        rental.setTotalPrice(19.90);

        var page = new PageImpl<>(List.of(rental), PageRequest.of(0, 10), 1);

        var result = rentalMapper.toDetailPage(page);

        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals("Fulano", result.content().get(0).customerName());
        assertEquals(1L, result.content().get(0).customerId());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
        assertEquals(1, result.totalElements());
        assertEquals(1, result.totalPages());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
    }

    @Test
    void toDetailPage_ShouldReturnEmptyPage_WhenPageIsNull() {
        var result = rentalMapper.toDetailPage(null);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
    }

    @Test
    void toDetailPage_ShouldReturnEmptyPage_WhenPageIsEmpty() {
        var page = new PageImpl<RentalEntity>(List.of(), PageRequest.of(0, 10), 0);

        var result = rentalMapper.toDetailPage(page);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
    }

}
