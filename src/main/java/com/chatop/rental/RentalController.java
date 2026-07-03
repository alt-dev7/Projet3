package com.chatop.rental;

import com.chatop.common.dto.SimpleMessageResponse;
import com.chatop.rental.dto.RentalCreateRequest;
import com.chatop.rental.dto.RentalResponse;
import com.chatop.rental.dto.RentalUpdateRequest;
import com.chatop.rental.dto.RentalsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Lister toutes les locations",
            description = "Renvoie la liste complète des locations disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des locations"),
            @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
    })
    @GetMapping
    public ResponseEntity<RentalsResponse> all() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @Operation(summary = "Récupérer une location par son id",
            description = "Renvoie le détail d'une location existante.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Détail de la location"),
            @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Aucune location avec cet id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RentalResponse> getById(
            @Parameter(description = "Identifiant de la location") @PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @Operation(summary = "Créer une nouvelle location",
            description = "Crée une location à partir d'un formulaire multipart/form-data "
                    + "(name, surface, price, description, picture) ; le propriétaire est déduit du token JWT.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location créée"),
            @ApiResponse(responseCode = "400", description = "Champ manquant/invalide ou image absente"),
            @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessageResponse> create(@Valid @ModelAttribute RentalCreateRequest request,
                                                          Authentication authentication) {
        return ResponseEntity.ok(rentalService.create(request, authentication.getName()));
    }

    @Operation(summary = "Mettre à jour une location existante",
            description = "Met à jour une location à partir d'un formulaire multipart/form-data "
                    + "(name, surface, price, description) ; l'image n'est pas modifiable via cette route.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Location mise à jour"),
            @ApiResponse(responseCode = "400", description = "Champ manquant ou invalide"),
            @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Aucune location avec cet id")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessageResponse> update(
            @Parameter(description = "Identifiant de la location") @PathVariable Integer id,
            @Valid @ModelAttribute RentalUpdateRequest request) {
        return ResponseEntity.ok(rentalService.update(id, request));
    }
}
