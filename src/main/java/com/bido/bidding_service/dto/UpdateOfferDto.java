package com.bido.bidding_service.dto;

import com.bido.bidding_service.enums.OfferStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateOfferDto(
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal totalPrice,
        @PositiveOrZero @Digits(integer = 10, fraction = 2) BigDecimal upfrontPayment,
        String description,
        OfferStatus status,
        boolean onlinePaymentAvailable) {
}
