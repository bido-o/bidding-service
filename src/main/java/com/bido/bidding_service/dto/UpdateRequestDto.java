package com.bido.bidding_service.dto;

import com.bido.bidding_service.model.LocationCity;
import com.bido.bidding_service.model.RequestStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateRequestDto(
        @Positive Integer nrPersons,
        @NotNull @DecimalMin("0.00") BigDecimal budgetTotal,
        Boolean budgetFlexible,
        @NotNull Instant eventDate,
        LocationCity locationCity,
        @Size(max = 255) String locationAddress,
        String message,
        Boolean wantsPackage,
        Boolean deliveryIncluded,
        Instant expiresAt,
        RequestStatus status) {
}
