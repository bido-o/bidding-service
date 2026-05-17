package com.bido.bidding_service.repository;

import com.bido.bidding_service.model.LocationCity;
import com.bido.bidding_service.model.Request;
import com.bido.bidding_service.model.RequestStatus;
import org.springframework.data.jpa.domain.Specification;

public final class RequestSpecifications {

    private RequestSpecifications() {}

    public static Specification<Request> hasStatus(RequestStatus status) {
        return status == null ? null : (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Request> hasClientId(Long clientId) {
        return clientId == null ? null : (root, query, cb) -> cb.equal(root.get("clientId"), clientId);
    }

    public static Specification<Request> hasCity(LocationCity city) {
        return city == null ? null : (root, query, cb) -> cb.equal(root.get("locationCity"), city);
    }
}
