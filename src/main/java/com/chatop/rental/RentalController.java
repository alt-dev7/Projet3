package com.chatop.rental;

import com.chatop.common.dto.SimpleMessageResponse;
import com.chatop.rental.dto.RentalCreateRequest;
import com.chatop.rental.dto.RentalResponse;
import com.chatop.rental.dto.RentalUpdateRequest;
import com.chatop.rental.dto.RentalsResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rentals")
@SecurityRequirement(name = "bearerAuth")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<RentalsResponse> all() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessageResponse> create(@Valid @ModelAttribute RentalCreateRequest request,
                                                          Authentication authentication) {
        return ResponseEntity.ok(rentalService.create(request, authentication.getName()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessageResponse> update(@PathVariable Integer id,
                                                          @Valid @ModelAttribute RentalUpdateRequest request) {
        return ResponseEntity.ok(rentalService.update(id, request));
    }
}
