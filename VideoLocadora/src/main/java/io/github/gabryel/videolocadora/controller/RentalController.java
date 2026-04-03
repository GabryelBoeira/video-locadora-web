package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.RentalApi;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.rental.RentalCreateDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalDetailDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalItemReturnDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalReturnDTO;
import io.github.gabryel.videolocadora.service.rent.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController implements RentalApi {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Override
    public ResponseEntity<RentalDetailDTO> create(@Valid @RequestBody RentalCreateDTO dto) throws BusinessException {
        var created = rentalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    public ResponseEntity<List<RentalDetailDTO>> list() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @Override
    public ResponseEntity<RentalDetailDTO> getById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @Override
    public ResponseEntity<Void> returnRental(@PathVariable Long id, @Valid @RequestBody RentalReturnDTO dto) throws BusinessException {
        rentalService.returnRental(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> returnRentalItem(
            @PathVariable Long id,
            @PathVariable Long itemId,
            @Valid @RequestBody RentalItemReturnDTO dto
    ) throws BusinessException {
        rentalService.returnRentalItem(id, itemId, dto);
        return ResponseEntity.noContent().build();
    }

}
