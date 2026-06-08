package com.bido.bidding_service.service;

import com.bido.bidding_service.dto.request.CreateRequestDto;
import com.bido.bidding_service.dto.request.ExtendExpiryDto;
import com.bido.bidding_service.dto.request.UpdateRequestDto;
import com.bido.bidding_service.exception.BusinessRuleException;
import com.bido.bidding_service.exception.ResourceNotFoundException;
import com.bido.bidding_service.mapper.RequestMapper;
import com.bido.bidding_service.model.EventType;
import com.bido.bidding_service.enums.LocationCity;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.enums.RequestStatus;
import com.bido.bidding_service.repository.OfferRepository;
import com.bido.bidding_service.repository.RequestRepository;
import com.bido.bidding_service.repository.RequestSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private final OfferRepository offerRepository;
    private final EventTypeService eventTypeService;
    private final RequestMapper requestMapper;

    public RequestService(RequestRepository requestRepository,
                          OfferRepository offerRepository,
                          EventTypeService eventTypeService,
                          RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.offerRepository = offerRepository;
        this.eventTypeService = eventTypeService;
        this.requestMapper = requestMapper;
    }

    public Request create(CreateRequestDto dto, Long clientId) {
        EventType eventType = eventTypeService.getById(dto.eventTypeId());
        Request request = requestMapper.toEntity(dto, clientId, eventType);
        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public Request findById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request", id));
    }

    @Transactional(readOnly = true)
    public List<Request> findAll(RequestStatus status, Long clientId, LocationCity locationCity) {
        Specification<Request> spec = Specification.allOf(
                RequestSpecifications.hasStatus(status),
                RequestSpecifications.hasClientId(clientId),
                RequestSpecifications.hasCity(locationCity));
        return requestRepository.findAll(spec);
    }

    //o cerere poate fi editată DOAR până la prima ofertă primită
    public Request update(Long id, UpdateRequestDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request", id));
        if (offerRepository.existsByRequestId(id)) {
            throw new BusinessRuleException(
                    "Cererea nu mai poate fi editată — a primit deja oferte. "
                            + "Anuleaz-o și creează una nouă dacă vrei să schimbi detaliile.");
        }
        EventType eventType = eventTypeService.getById(dto.eventTypeId());
        requestMapper.applyUpdate(request, dto, eventType);
        return request;
    }

    //Anularea cererii de către client OPEN → CANCELLED.
    public Request cancel(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request", id));
        if (request.getStatus() != RequestStatus.OPEN) {
            throw new BusinessRuleException(
                    "Doar o cerere activă poate fi anulată. Status curent: "
                            + request.getStatus() + ".");
        }
        request.setStatus(RequestStatus.CANCELLED);
        return request;
    }

    public Request extendExpiry(Long id, ExtendExpiryDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request", id));
        if (request.getStatus() != RequestStatus.OPEN && request.getStatus() != RequestStatus.EXPIRED) {
            throw new BusinessRuleException(
                    "Termenul poate fi prelungit doar pentru cereri active sau expirate. Status curent: "
                            + request.getStatus() + ".");
        }
        Instant newExpiresAt = dto.expiresAt();
        if (newExpiresAt == null || !newExpiresAt.isAfter(Instant.now())) {
            throw new BusinessRuleException("Noul termen de expirare trebuie să fie în viitor.");
        }
        request.setExpiresAt(newExpiresAt);
        request.setStatus(RequestStatus.OPEN);
        return request;
    }

    // TODO(CLOSED): la acceptarea unei oferte cererea trebuie trecută automat OPEN → CLOSED
    // TODO(EXPIRED)

    public void delete(Long id) {
        if (!requestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Request", id);
        }
        requestRepository.deleteById(id);
    }
}
