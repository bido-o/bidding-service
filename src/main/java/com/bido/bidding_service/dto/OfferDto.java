package com.bido.bidding_service.dto;

import com.bido.bidding_service.model.OfferStatus;

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
        Boolean onlinePaymentAvailable,
        Integer creditsUsed,
        Instant createdAt) {
}
