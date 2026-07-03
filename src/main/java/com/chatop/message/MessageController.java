package com.chatop.message;

import com.chatop.common.dto.SimpleMessageResponse;
import com.chatop.message.dto.MessageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@Tag(name = "Messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Envoyer un message à propos d'une location",
            description = "Enregistre un message envoyé par un utilisateur au sujet d'une location.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Message envoyé"),
            @ApiResponse(responseCode = "400", description = "Champ manquant (rental_id, user_id ou message)"),
            @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
    })
    @PostMapping
    public ResponseEntity<SimpleMessageResponse> send(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(messageService.send(request));
    }
}
