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
        boolean wantsPackage,
        Boolean deliveryIncluded,
        Instant createdAt,
        Instant expiresAt,
        RequestStatus status,
        Long eventTypeId,
        Long clientId) {
}
