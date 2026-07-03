package com.chatop.rental.dto;

import com.chatop.rental.Rental;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class RentalResponse {

    private final Integer id;
    private final String name;
    private final BigDecimal surface;
    private final BigDecimal price;
    private final String picture;
    private final String description;

    @JsonProperty("owner_id")
    private final Integer ownerId;

    @JsonProperty("created_at")
    private final LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private final LocalDateTime updatedAt;

    public RentalResponse(Rental rental) {
        this.id = rental.getId();
        this.name = rental.getName();
        this.surface = rental.getSurface();
        this.price = rental.getPrice();
        this.picture = rental.getPicture();
        this.description = rental.getDescription();
        this.ownerId = rental.getOwnerId();
        this.createdAt = rental.getCreatedAt();
        this.updatedAt = rental.getUpdatedAt();
    }
}
