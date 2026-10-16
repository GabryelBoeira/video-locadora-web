package io.github.gabryel.videolocadora.controller;

import io.github.gabryel.videolocadora.controller.api.RentalApi;
import io.github.gabryel.videolocadora.exception.BusinessException;
import io.github.gabryel.videolocadora.model.dto.hateoas.Link;
import io.github.gabryel.videolocadora.model.dto.hateoas.Resource;
import io.github.gabryel.videolocadora.model.dto.hateoas.ResourceCollection;
import io.github.gabryel.videolocadora.model.dto.rental.RentalCreateDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalDetailDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalItemReturnDTO;
import io.github.gabryel.videolocadora.model.dto.rental.RentalReturnDTO;
import io.github.gabryel.videolocadora.model.mapper.hateoas.LinkBuilder;
import io.github.gabryel.videolocadora.service.rent.RentalService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rentals")
public class RentalController implements RentalApi {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Override
    public ResponseEntity<Resource<RentalDetailDTO>> create(@Valid @RequestBody RentalCreateDTO dto) throws BusinessException {
        var created = rentalService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResource(created));
    }

    @Override
    public ResponseEntity<ResourceCollection<RentalDetailDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(ResourceCollection.of(rentalService.findAllPaginated(pageable), "/rentals", this::rentalLinks));
    }

    @Override
    public ResponseEntity<Resource<RentalDetailDTO>> getById(@PathVariable Long id) throws BusinessException {
        return ResponseEntity.ok(toResource(rentalService.findById(id)));
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

    private Resource<RentalDetailDTO> toResource(RentalDetailDTO rental) {
        return Resource.of(rental, rentalLinks(rental));
    }

    private Map<String, Link> rentalLinks(RentalDetailDTO rental) {
        return LinkBuilder.from("/rentals", rental.id())
                .self()
                .custom("return", "/return", "POST")
                .build();
    }

}
