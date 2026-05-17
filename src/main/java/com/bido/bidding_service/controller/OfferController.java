package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.CreateOfferDto;
import com.bido.bidding_service.dto.OfferDto;
import com.bido.bidding_service.dto.UpdateOfferDto;
import com.bido.bidding_service.mapper.OfferMapper;
import com.bido.bidding_service.model.Offer;
import com.bido.bidding_service.service.OfferService;
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
    private final OfferMapper offerMapper;

    public OfferController(OfferService offerService, OfferMapper offerMapper) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
    }

    @PostMapping("/requests/{requestId}/offers")
    public ResponseEntity<OfferDto> create(@PathVariable Long requestId,
                                           @Valid @RequestBody CreateOfferDto dto,
                                           UriComponentsBuilder uriBuilder) {
        Offer offer = offerService.create(requestId, dto);
        return ResponseEntity
                .created(uriBuilder.path("/api/offers/{id}").buildAndExpand(offer.getId()).toUri())
                .body(offerMapper.toDto(offer));
    }

    @GetMapping("/requests/{requestId}/offers")
    public List<OfferDto> findByRequest(@PathVariable Long requestId) {
        return offerService.findByRequestId(requestId)
                .stream().map(offerMapper::toDto).toList();
    }

    @GetMapping("/offers/{id}")
    public OfferDto findById(@PathVariable Long id) {
        return offerMapper.toDto(offerService.findById(id));
    }

    @PutMapping("/offers/{id}")
    public OfferDto update(@PathVariable Long id, @Valid @RequestBody UpdateOfferDto dto) {
        return offerMapper.toDto(offerService.update(id, dto));
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
