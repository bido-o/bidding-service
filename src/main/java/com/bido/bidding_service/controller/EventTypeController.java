package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.eventtype.EventTypeDto;
import com.bido.bidding_service.service.EventTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event-types")
public class EventTypeController {

    private final EventTypeService eventTypeService;

    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @GetMapping
    public List<EventTypeDto> findAll() {
        return eventTypeService.findAll();
    }
}
