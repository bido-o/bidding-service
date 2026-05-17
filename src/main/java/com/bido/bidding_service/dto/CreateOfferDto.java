package com.bido.bidding_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateOfferDto(
        @NotNull @DecimalMin("0.00") BigDecimal totalPrice,
        @DecimalMin("0.00") BigDecimal upfrontPayment,
        String description,
        Boolean onlinePaymentAvailable,
        @PositiveOrZero Integer creditsUsed,
        @NotNull Long supplierProfileId) {
}
