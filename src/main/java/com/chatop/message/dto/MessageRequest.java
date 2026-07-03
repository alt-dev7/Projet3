package com.chatop.message.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {

    @NotNull
    @JsonProperty("rental_id")
    private Integer rentalId;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotBlank
    private String message;
}
