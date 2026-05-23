package com.bido.bidding_service.controller;

import com.bido.bidding_service.dto.CreateRequestDto;
import com.bido.bidding_service.dto.RequestDto;
import com.bido.bidding_service.dto.UpdateRequestDto;
import com.bido.bidding_service.mapper.RequestMapper;
import com.bido.bidding_service.model.LocationCity;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.model.RequestStatus;
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
        auth.requireAdminOrOwner(AuthContext.ROLE_CLIENT, dto.clientId());
        Request created = requestService.create(dto);
        RequestDto body = requestMapper.toDto(created);
        return ResponseEntity
                .created(uriBuilder.path("/api/requests/{id}").buildAndExpand(created.getId()).toUri())
                .body(body);
    }

    @GetMapping("/{id}")
    public RequestDto findById(@PathVariable Long id, AuthContext auth) {
        Request request = requestService.findById(id);
        if (!auth.isAdmin()
                && !auth.isSupplier()
                && !(auth.isClient() && auth.isOwner(request.getClientId()))) {
            throw AuthContext.forbidden();
        }
        return requestMapper.toDto(request);
    }

    @GetMapping
    public List<RequestDto> findAll(
            @RequestParam(required = false) RequestStatus status,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) LocationCity locationCity,
            AuthContext auth) {
        if (auth.isSupplier()) {
            status = RequestStatus.OPEN;
        } else if (auth.isClient()) {
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
