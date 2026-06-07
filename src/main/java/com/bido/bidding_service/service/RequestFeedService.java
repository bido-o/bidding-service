package com.bido.bidding_service.service;

import com.bido.bidding_service.enums.LocationCity;
import com.bido.bidding_service.enums.RequestStatus;
import com.bido.bidding_service.exception.ResourceNotFoundException;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.repository.RequestRepository;
import com.bido.bidding_service.repository.RequestSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RequestFeedService {

    private final RequestRepository requestRepository;

    public RequestFeedService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findOpenFeed(LocationCity locationCity) {
        Specification<Request> spec = Specification.allOf(
                RequestSpecifications.hasStatus(RequestStatus.OPEN),
                RequestSpecifications.hasCity(locationCity));
        return requestRepository.findAll(spec);
    }

    //TODO: permite și cereri non-OPEN dacă furnizorul a trimis deja o ofertă la ele.)
    public Request findOpenById(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request", id));
        if (request.getStatus() != RequestStatus.OPEN) {
            throw new ResourceNotFoundException("Request", id);
        }
        return request;
    }
}
