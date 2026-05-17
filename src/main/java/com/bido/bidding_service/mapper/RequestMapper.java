package com.bido.bidding_service.mapper;

import com.bido.bidding_service.dto.CreateRequestDto;
import com.bido.bidding_service.dto.RequestDto;
import com.bido.bidding_service.dto.UpdateRequestDto;
import com.bido.bidding_service.model.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public Request toEntity(CreateRequestDto dto) {
        Request request = new Request();
        request.setNrPersons(dto.nrPersons());
        request.setBudgetTotal(dto.budgetTotal());
        request.setBudgetFlexible(Boolean.TRUE.equals(dto.budgetFlexible()));
        request.setEventDate(dto.eventDate());
        request.setLocationCity(dto.locationCity());
        request.setLocationAddress(dto.locationAddress());
        request.setMessage(dto.message());
        request.setWantsPackage(Boolean.TRUE.equals(dto.wantsPackage()));
        request.setDeliveryIncluded(dto.deliveryIncluded());
        request.setExpiresAt(dto.expiresAt());
        request.setClientId(dto.clientId());
        return request;
    }

    public void applyUpdate(Request request, UpdateRequestDto dto) {
        request.setNrPersons(dto.nrPersons());
        request.setBudgetTotal(dto.budgetTotal());
        request.setBudgetFlexible(Boolean.TRUE.equals(dto.budgetFlexible()));
        request.setEventDate(dto.eventDate());
        request.setLocationCity(dto.locationCity());
        request.setLocationAddress(dto.locationAddress());
        request.setMessage(dto.message());
        request.setWantsPackage(Boolean.TRUE.equals(dto.wantsPackage()));
        request.setDeliveryIncluded(dto.deliveryIncluded());
        request.setExpiresAt(dto.expiresAt());
        if (dto.status() != null) {
            request.setStatus(dto.status());
        }
    }

    public RequestDto toDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getNrPersons(),
                request.getBudgetTotal(),
                request.isBudgetFlexible(),
                request.getEventDate(),
                request.getLocationCity(),
                request.getLocationAddress(),
                request.getMessage(),
                request.isWantsPackage(),
                request.getDeliveryIncluded(),
                request.getCreatedAt(),
                request.getExpiresAt(),
                request.getStatus(),
                request.getClientId());
    }
}
