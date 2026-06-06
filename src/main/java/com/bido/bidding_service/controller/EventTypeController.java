package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.EventTypeDto;
import com.bido.bidding_service.repository.EventTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event-types")
public class EventTypeController {

    private final EventTypeRepository eventTypeRepository;

    public EventTypeController(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @GetMapping
    public List<EventTypeDto> findAll() {
        return eventTypeRepository.findAllByOrderByNameAsc()
                .stream()
                .map(et -> new EventTypeDto(et.getId(), et.getName()))
                .toList();
    }
}
