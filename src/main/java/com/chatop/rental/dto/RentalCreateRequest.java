package com.chatop.rental.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class RentalCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal surface;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    private MultipartFile picture;
}
