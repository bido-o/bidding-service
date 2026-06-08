package com.bido.bidding_service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

// Body pentru prelungirea termenului unei cereri (POST /api/requests/{id}/extend-expiry).
public record ExtendExpiryDto(
        @NotNull @Future Instant expiresAt) {
}
