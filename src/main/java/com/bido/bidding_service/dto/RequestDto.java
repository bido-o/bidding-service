package com.bido.bidding_service.dto;

import com.bido.bidding_service.model.LocationCity;
import com.bido.bidding_service.model.RequestStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record RequestDto(
        Long id,
        Integer nrPersons,
        BigDecimal budgetTotal,
        boolean budgetFlexible,
        Instant eventDate,
        LocationCity locationCity,
        String locationAddress,
        String message,
        boolean deliveryIncluded,
        Instant createdAt,
        Instant updatedAt,
        Instant expiresAt,
        RequestStatus status,
        Long clientId,
        Long eventTypeId,
        String eventTypeName) {
}
