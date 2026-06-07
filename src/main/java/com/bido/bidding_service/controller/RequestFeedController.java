package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.request.RequestPublicDto;
import com.bido.bidding_service.enums.LocationCity;
import com.bido.bidding_service.mapper.RequestMapper;
import com.bido.bidding_service.security.AuthContext;
import com.bido.bidding_service.service.RequestFeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Feed-ul public de licitație — furnizorii răsfoiesc cererile OPEN.

@RestController
@RequestMapping("/api/requests/feed")
public class RequestFeedController {

    private final RequestFeedService requestFeedService;
    private final RequestMapper requestMapper;

    public RequestFeedController(RequestFeedService requestFeedService, RequestMapper requestMapper) {
        this.requestFeedService = requestFeedService;
        this.requestMapper = requestMapper;
    }

    @GetMapping
    public List<RequestPublicDto> feed( @RequestParam(required = false) LocationCity locationCity, AuthContext auth) {
        requireSupplier(auth);
        return requestFeedService.findOpenFeed(locationCity)
                .stream().map(requestMapper::toPublicDto).toList();
    }

    @GetMapping("/{id}")
    public RequestPublicDto findById(@PathVariable Long id, AuthContext auth) {
        requireSupplier(auth);
        return requestMapper.toPublicDto(requestFeedService.findOpenById(id));
    }

    private void requireSupplier(AuthContext auth) {
        if (!auth.isSupplier()) {
            throw AuthContext.forbidden();
        }
    }
}
