package com.bido.bidding_service.dto;

import com.bido.bidding_service.model.LocationCity;
import com.bido.bidding_service.model.RequestStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateRequestDto(
        @Positive Integer nrPersons,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal budgetTotal,
        boolean budgetFlexible,
        @NotNull Instant eventDate,
        LocationCity locationCity,
        @Size(max = 255) String locationAddress,
        String message,
        boolean deliveryIncluded,
        Instant expiresAt,
        @NotNull @Positive Long eventTypeId,
        RequestStatus status) {
}
