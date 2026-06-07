package com.bido.bidding_service.dto.offer;

import com.bido.bidding_service.enums.OfferStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record OfferDto(
        Long id,
        Long requestId,
        Long supplierProfileId,
        BigDecimal totalPrice,
        BigDecimal upfrontPayment,
        String description,
        OfferStatus status,
        boolean onlinePaymentAvailable,
        Instant createdAt,
        Instant updatedAt) {
}
