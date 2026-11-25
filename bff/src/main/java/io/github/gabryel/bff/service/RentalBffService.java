package io.github.gabryel.bff.service;

import io.github.gabryel.bff.client.RentalClient;
import io.github.gabryel.bff.dto.page.PageResultDTO;
import io.github.gabryel.bff.dto.rental.RentalCreateRequestDTO;
import io.github.gabryel.bff.dto.rental.RentalReturnRequestDTO;
import io.github.gabryel.bff.dto.rental.RentalSummaryDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RentalBffService {

    private final RentalClient rentalClient;

    public RentalBffService(RentalClient rentalClient) {
        this.rentalClient = rentalClient;
    }

    public PageResultDTO<RentalSummaryDTO> findAll(Pageable pageable) {
        return rentalClient.findAll(pageable);
    }

    public RentalSummaryDTO findById(Long id) {
        return rentalClient.findById(id);
    }

    public RentalSummaryDTO create(RentalCreateRequestDTO request) {
        return rentalClient.create(request);
    }

    public void returnRental(Long id, RentalReturnRequestDTO request) {
        rentalClient.returnRental(id, request);
    }

    public void returnItem(Long id, Long itemId, RentalReturnRequestDTO request) {
        rentalClient.returnItem(id, itemId, request);
    }

}
