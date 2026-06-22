package com.bido.bidding_service.repository;

import com.bido.bidding_service.enums.LocationCity;
import com.bido.bidding_service.enums.RequestStatus;
import com.bido.bidding_service.model.Request;
import org.springframework.data.jpa.domain.Specification;

public final class RequestSpecifications {

    private RequestSpecifications() {}

    public static Specification<Request> hasStatus(RequestStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Request> hasClientId(Long clientId) {
        return (root, query, cb) ->
                clientId == null ? cb.conjunction() : cb.equal(root.get("clientId"), clientId);
    }

    public static Specification<Request> hasCity(LocationCity city) {
        return (root, query, cb) ->
                city == null ? cb.conjunction() : cb.equal(root.get("locationCity"), city);
    }
}
