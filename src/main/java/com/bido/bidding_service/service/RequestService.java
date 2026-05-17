package com.bido.bidding_service.service;

import com.bido.bidding_service.dto.CreateRequestDto;
import com.bido.bidding_service.dto.UpdateRequestDto;
import com.bido.bidding_service.exception.ResourceNotFoundException;
import com.bido.bidding_service.mapper.RequestMapper;
import com.bido.bidding_service.model.LocationCity;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.model.RequestStatus;
import com.bido.bidding_service.repository.RequestRepository;
import com.bido.bidding_service.repository.RequestSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    public RequestService(RequestRepository requestRepository, RequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    public Request create(CreateRequestDto dto) {
        Request request = requestMapper.toEntity(dto);
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

    public Request update(Long id, UpdateRequestDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request", id));
        requestMapper.applyUpdate(request, dto);
        return request;
    }

    public void delete(Long id) {
        if (!requestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Request", id);
        }
        requestRepository.deleteById(id);
    }
}
