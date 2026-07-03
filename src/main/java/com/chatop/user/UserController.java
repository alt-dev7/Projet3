package com.chatop.user;

import com.chatop.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Récupérer un utilisateur par son id",
            description = "Renvoie les informations publiques d'un utilisateur, notamment le propriétaire d'une location.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Informations de l'utilisateur"),
            @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré"),
            @ApiResponse(responseCode = "404", description = "Aucun utilisateur avec cet id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(
            @Parameter(description = "Identifiant de l'utilisateur") @PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
