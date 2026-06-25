package com.bido.bidding_service.dto.offer;

import com.bido.bidding_service.enums.OfferStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Oferta văzută din perspectiva furnizorului care a trimis-o, îmbogățită cu
 * context din cererea pe care s-a ofertat (titlu + mesaj), ca furnizorul să nu
 * fie nevoit să facă un request separat pentru fiecare ofertă.
 */
public record SentOfferDto(
        Long id,
        Long requestId,
        Long supplierProfileId,
        BigDecimal totalPrice,
        BigDecimal upfrontPayment,
        String description,
        OfferStatus status,
        boolean onlinePaymentAvailable,
        Instant createdAt,
        Instant updatedAt,
        String eventTypeName,
        String requestMessage) {
}
