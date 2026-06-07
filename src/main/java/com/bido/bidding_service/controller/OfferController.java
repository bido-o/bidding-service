package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.offer.CreateOfferDto;
import com.bido.bidding_service.dto.offer.OfferDto;
import com.bido.bidding_service.dto.offer.UpdateOfferDto;
import com.bido.bidding_service.mapper.OfferMapper;
import com.bido.bidding_service.model.Offer;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.security.AuthContext;
import com.bido.bidding_service.service.OfferService;
import com.bido.bidding_service.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OfferController {

    private final OfferService offerService;
    private final RequestService requestService;
    private final OfferMapper offerMapper;

    public OfferController(OfferService offerService,
                           RequestService requestService,
                           OfferMapper offerMapper) {
        this.offerService = offerService;
        this.requestService = requestService;
        this.offerMapper = offerMapper;
    }

    @PostMapping("/requests/{requestId}/offers")
    public ResponseEntity<OfferDto> create(@PathVariable Long requestId,
                                           @Valid @RequestBody CreateOfferDto dto,
                                           UriComponentsBuilder uriBuilder,
                                           AuthContext auth) {
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
        Offer offer = offerService.create(requestId, dto, auth.userId());
        return ResponseEntity
                .created(uriBuilder.path("/api/offers/{id}").buildAndExpand(offer.getId()).toUri())
                .body(offerMapper.toDto(offer));
    }

    @GetMapping("/requests/{requestId}/offers")
    public List<OfferDto> findByRequest(@PathVariable Long requestId, AuthContext auth) {
        if (!auth.isAdmin()) {
            Request request = requestService.findById(requestId);
            boolean canView = auth.isClient() && auth.isOwner(request.getClientId());
            if (!canView) {
                throw AuthContext.forbidden();
            }
        }
        return offerService.findByRequestId(requestId)
                .stream().map(offerMapper::toDto).toList();
    }

    @GetMapping("/offers/{id}")
    public OfferDto findById(@PathVariable Long id, AuthContext auth) {
        Offer offer = offerService.findById(id);
        if (!canViewOffer(auth, offer)) {
            throw AuthContext.forbidden();
        }
        return offerMapper.toDto(offer);
    }

    @PutMapping("/offers/{id}")
    public OfferDto update(@PathVariable Long id, @Valid @RequestBody UpdateOfferDto dto, AuthContext auth) {
        Offer existing = offerService.findById(id);
        if (!auth.isAdmin() && !(auth.isSupplier() && auth.isOwner(existing.getSupplierProfileId()))) {
            throw AuthContext.forbidden();
        }
        return offerMapper.toDto(offerService.update(id, dto));
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, AuthContext auth) {
        Offer existing = offerService.findById(id);
        if (!auth.isAdmin() && !(auth.isSupplier() && auth.isOwner(existing.getSupplierProfileId()))) {
            throw AuthContext.forbidden();
        }
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private boolean canViewOffer(AuthContext auth, Offer offer) {
        if (auth.isAdmin()) {
            return true;
        }
        if (auth.isSupplier() && auth.isOwner(offer.getSupplierProfileId())) {
            return true;
        }
        if (auth.isClient()) {
            Long clientId = requestService.findById(offer.getRequest().getId()).getClientId();
            return auth.isOwner(clientId);
        }
        return false;
    }
}
