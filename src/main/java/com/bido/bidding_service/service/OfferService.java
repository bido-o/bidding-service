package com.bido.bidding_service.service;

import com.bido.bidding_service.dto.CreateOfferDto;
import com.bido.bidding_service.dto.UpdateOfferDto;
import com.bido.bidding_service.exception.BusinessRuleException;
import com.bido.bidding_service.exception.ResourceNotFoundException;
import com.bido.bidding_service.mapper.OfferMapper;
import com.bido.bidding_service.model.Offer;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.enums.RequestStatus;
import com.bido.bidding_service.repository.OfferRepository;
import com.bido.bidding_service.repository.RequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OfferService {

    private final OfferRepository offerRepository;
    private final RequestRepository requestRepository;
    private final OfferMapper offerMapper;

    public OfferService(OfferRepository offerRepository,
                        RequestRepository requestRepository,
                        OfferMapper offerMapper) {
        this.offerRepository = offerRepository;
        this.requestRepository = requestRepository;
        this.offerMapper = offerMapper;
    }

    public Offer create(Long requestId, CreateOfferDto dto, Long supplierProfileId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request", requestId));
        if (request.getStatus() != RequestStatus.OPEN) {
            throw new BusinessRuleException(
                    "Offers can only be created on requests with status OPEN (was %s)."
                            .formatted(request.getStatus()));
        }
        Offer offer = offerMapper.toEntity(dto, request, supplierProfileId);
        return offerRepository.save(offer);
    }

    @Transactional(readOnly = true)
    public Offer findById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer", id));
    }

    @Transactional(readOnly = true)
    public List<Offer> findByRequestId(Long requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new ResourceNotFoundException("Request", requestId);
        }
        return offerRepository.findAllByRequestId(requestId);
    }

    public Offer update(Long id, UpdateOfferDto dto) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer", id));
        offerMapper.applyUpdate(offer, dto);
        return offer;
    }


    public void delete(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Offer", id);
        }
        offerRepository.deleteById(id);
    }
}
