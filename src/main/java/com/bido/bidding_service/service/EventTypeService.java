package com.bido.bidding_service.service;

import com.bido.bidding_service.dto.eventtype.EventTypeDto;
import com.bido.bidding_service.exception.ResourceNotFoundException;
import com.bido.bidding_service.model.EventType;
import com.bido.bidding_service.repository.EventTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventTypeService {

    private final EventTypeRepository eventTypeRepository;

    public EventTypeService(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    public List<EventTypeDto> findAll() {
        return eventTypeRepository.findAllByOrderByNameAsc()
                .stream()
                .map(et -> new EventTypeDto(et.getId(), et.getName()))
                .toList();
    }

    public EventType getById(Long id) {
        return eventTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EventType", id));
    }
}
