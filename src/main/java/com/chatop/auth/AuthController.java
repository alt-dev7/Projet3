package com.chatop.auth;

import com.chatop.auth.dto.AuthResponse;
import com.chatop.auth.dto.LoginRequest;
import com.chatop.auth.dto.RegisterRequest;
import com.chatop.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Le front-end Angular (AppComponent.autoLog(), non modifié ici) appelle cette route
    // systématiquement à chaque démarrage de l'app pour tenter de restaurer une session.
    // Tant qu'aucun utilisateur n'est connecté, elle renvoie légitimement 401, ce qui apparaît
    // comme une erreur réseau rouge dans la console du navigateur — c'est un comportement
    // attendu du front, pas un bug côté back, et impossible à supprimer sans modifier le front.
    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        return ResponseEntity.ok(authService.me(authentication.getName()));
    }
}
