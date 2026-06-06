package com.bido.bidding_service.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateOfferDto(
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal totalPrice,
        @PositiveOrZero @Digits(integer = 10, fraction = 2) BigDecimal upfrontPayment,
        String description,
        boolean onlinePaymentAvailable) {
}
