package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.request.CreateRequestDto;
import com.bido.bidding_service.dto.request.RequestDto;
import com.bido.bidding_service.dto.request.UpdateRequestDto;
import com.bido.bidding_service.mapper.RequestMapper;
import com.bido.bidding_service.enums.LocationCity;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.enums.RequestStatus;
import com.bido.bidding_service.security.AuthContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final RequestMapper requestMapper;

    public RequestController(RequestService requestService, RequestMapper requestMapper) {
        this.requestService = requestService;
        this.requestMapper = requestMapper;
    }

    @PostMapping
    public ResponseEntity<RequestDto> create(@Valid @RequestBody CreateRequestDto dto,
                                             UriComponentsBuilder uriBuilder,
                                             AuthContext auth) {
        if (!auth.isClient()) {
            throw AuthContext.forbidden();
        }
        Request created = requestService.create(dto, auth.userId());
        RequestDto body = requestMapper.toDto(created);
        return ResponseEntity
                .created(uriBuilder.path("/api/requests/{id}").buildAndExpand(created.getId()).toUri())
                .body(body);
    }

    @GetMapping("/{id}")
    public RequestDto findById(@PathVariable Long id, AuthContext auth) {
        Request request = requestService.findById(id);
        if (auth.isAdmin() || (auth.isClient() && auth.isOwner(request.getClientId()))) {
            return requestMapper.toDto(request);
        }
        throw AuthContext.forbidden();
    }

    @GetMapping
    public List<RequestDto> findAll(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) LocationCity locationCity,
            AuthContext auth) {

        // clientul își vede doar propriile cereri; adminul le vede pe toate
        if (auth.isClient()) {
            clientId = auth.userId();
        } else if (!auth.isAdmin()) {
            throw AuthContext.forbidden();
        }
        return requestService.findAll(status, clientId, locationCity)
                .stream().map(requestMapper::toDto).toList();
    }

    @PutMapping("/{id}")
    public RequestDto update(@PathVariable Long id, @Valid @RequestBody UpdateRequestDto dto, AuthContext auth) {
        Request existing = requestService.findById(id);
        //VERIFICARE CA STATUS != OPEN
        if (!auth.isAdmin() && !(auth.isClient() && auth.isOwner(existing.getClientId()))) {
            throw AuthContext.forbidden();
        }
        return requestMapper.toDto(requestService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, AuthContext auth) {
        Request existing = requestService.findById(id);
        if (!auth.isAdmin() && !(auth.isClient() && auth.isOwner(existing.getClientId()))) {
            throw AuthContext.forbidden();
        }
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
