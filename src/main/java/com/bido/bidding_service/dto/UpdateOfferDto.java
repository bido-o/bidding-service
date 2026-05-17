package com.bido.bidding_service.dto;

import com.bido.bidding_service.model.OfferStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateOfferDto(
        @NotNull @DecimalMin("0.00") BigDecimal totalPrice,
        @DecimalMin("0.00") BigDecimal upfrontPayment,
        String description,
        OfferStatus status,
        Boolean onlinePaymentAvailable,
        @PositiveOrZero Integer creditsUsed) {
}
