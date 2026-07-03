package com.chatop.rental.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RentalsResponse {

    private final List<RentalResponse> rentals;

    public RentalsResponse(List<RentalResponse> rentals) {
        this.rentals = rentals;
    }
}
