package com.bido.bidding_service.dto;

import com.bido.bidding_service.enums.LocationCity;
import com.bido.bidding_service.enums.RequestStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Proiecția cererii pentru furnizori
 * Față de RequestDto, omite intenționat:
 *   - clientId        → furnizorul nu trebuie să știe cine a postat cererea
 *   - locationAddress → adresa exactă se dezvăluie abia după acceptarea unei oferte
 *   - updatedAt       → detaliu intern, irelevant pentru furnizor
 */
public record RequestPublicDto(
        Long id,
        Integer nrPersons,
        BigDecimal budgetTotal,
        boolean budgetFlexible,
        Instant eventDate,
        LocationCity locationCity,
        String message,
        boolean deliveryIncluded,
        Instant createdAt,
        Instant expiresAt,
        RequestStatus status,
        Long eventTypeId,
        String eventTypeName) {
}
