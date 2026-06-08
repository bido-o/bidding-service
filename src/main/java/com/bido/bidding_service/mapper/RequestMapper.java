package com.bido.bidding_service.mapper;

import com.bido.bidding_service.dto.request.CreateRequestDto;
import com.bido.bidding_service.dto.request.RequestDto;
import com.bido.bidding_service.dto.request.RequestPublicDto;
import com.bido.bidding_service.dto.request.UpdateRequestDto;
import com.bido.bidding_service.model.EventType;
import com.bido.bidding_service.model.Request;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public Request toEntity(CreateRequestDto dto, Long clientId, EventType eventType) {
        Request request = new Request();
        request.setNrPersons(dto.nrPersons());
        request.setBudgetTotal(dto.budgetTotal());
        request.setBudgetFlexible(dto.budgetFlexible());
        request.setEventDate(dto.eventDate());
        request.setLocationCity(dto.locationCity());
        request.setLocationAddress(dto.locationAddress());
        request.setMessage(dto.message());
        request.setDeliveryIncluded(dto.deliveryIncluded());
        request.setExpiresAt(dto.expiresAt());
        request.setClientId(clientId);
        request.setEventType(eventType);
        return request;
    }

    public void applyUpdate(Request request, UpdateRequestDto dto, EventType eventType) {
        request.setNrPersons(dto.nrPersons());
        request.setBudgetTotal(dto.budgetTotal());
        request.setBudgetFlexible(dto.budgetFlexible());
        request.setEventDate(dto.eventDate());
        request.setLocationCity(dto.locationCity());
        request.setLocationAddress(dto.locationAddress());
        request.setMessage(dto.message());
        request.setDeliveryIncluded(dto.deliveryIncluded());
        request.setExpiresAt(dto.expiresAt());
        request.setEventType(eventType);
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
                request.isDeliveryIncluded(),
                request.getCreatedAt(),
                request.getUpdatedAt(),
                request.getExpiresAt(),
                request.getStatus(),
                request.getClientId(),
                request.getEventType().getId(),
                request.getEventType().getName());
    }

    public RequestPublicDto toPublicDto(Request request) {
        return new RequestPublicDto(
                request.getId(),
                request.getNrPersons(),
                request.getBudgetTotal(),
                request.isBudgetFlexible(),
                request.getEventDate(),
                request.getLocationCity(),
                request.getMessage(),
                request.isDeliveryIncluded(),
                request.getCreatedAt(),
                request.getExpiresAt(),
                request.getStatus(),
                request.getEventType().getId(),
                request.getEventType().getName());
    }
}
