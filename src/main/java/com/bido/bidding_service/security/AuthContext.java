package com.bido.bidding_service.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record AuthContext(Long userId, String role, String email) {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CLIENT = "CLIENT";
    public static final String ROLE_SUPPLIER = "SUPPLIER";

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(role);
    }

    public boolean isClient() {
        return ROLE_CLIENT.equals(role);
    }

    public boolean isSupplier() {
        return ROLE_SUPPLIER.equals(role);
    }

    public boolean isOwner(Long ownerId) {
        return userId != null && userId.equals(ownerId);
    }

    public void requireAdmin() {
        if (!isAdmin()) {
            throw forbidden();
        }
    }

    public void requireAdminOrOwner(String requiredRole, Long ownerId) {
        if (isAdmin()) {
            return;
        }
        if (requiredRole.equals(role) && isOwner(ownerId)) {
            return;
        }
        throw forbidden();
    }

    public static ResponseStatusException forbidden() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
