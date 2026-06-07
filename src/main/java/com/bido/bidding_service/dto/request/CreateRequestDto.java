package com.bido.bidding_service.dto.request;

import com.bido.bidding_service.enums.LocationCity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateRequestDto(
        @Positive Integer nrPersons,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal budgetTotal,
        boolean budgetFlexible,
        @NotNull Instant eventDate,
        LocationCity locationCity,
        @Size(max = 255) String locationAddress,
        String message,
        boolean deliveryIncluded,
        Instant expiresAt,
        @NotNull @Positive Long eventTypeId) {
}
