package com.bido.bidding_service.mapper;

import com.bido.bidding_service.dto.CreateOfferDto;
import com.bido.bidding_service.dto.OfferDto;
import com.bido.bidding_service.dto.UpdateOfferDto;
import com.bido.bidding_service.model.Offer;
import com.bido.bidding_service.model.Request;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OfferMapper {

    public Offer toEntity(CreateOfferDto dto, Request request) {
        Offer offer = new Offer();
        offer.setRequest(request);
        offer.setSupplierProfileId(dto.supplierProfileId());
        offer.setTotalPrice(dto.totalPrice());
        offer.setUpfrontPayment(dto.upfrontPayment() != null ? dto.upfrontPayment() : BigDecimal.ZERO);
        offer.setDescription(dto.description());
        offer.setOnlinePaymentAvailable(dto.onlinePaymentAvailable());
        offer.setCreditsUsed(dto.creditsUsed());
        return offer;
    }

    public void applyUpdate(Offer offer, UpdateOfferDto dto) {
        offer.setTotalPrice(dto.totalPrice());
        offer.setUpfrontPayment(dto.upfrontPayment() != null ? dto.upfrontPayment() : BigDecimal.ZERO);
        offer.setDescription(dto.description());
        if (dto.status() != null) {
            offer.setStatus(dto.status());
        }
        offer.setOnlinePaymentAvailable(dto.onlinePaymentAvailable());
        offer.setCreditsUsed(dto.creditsUsed());
    }

    public OfferDto toDto(Offer offer) {
        return new OfferDto(
                offer.getId(),
                offer.getRequest().getId(),
                offer.getSupplierProfileId(),
                offer.getTotalPrice(),
                offer.getUpfrontPayment(),
                offer.getDescription(),
                offer.getStatus(),
                offer.getOnlinePaymentAvailable(),
                offer.getCreditsUsed(),
                offer.getCreatedAt());
    }
}
