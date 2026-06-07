package com.bido.bidding_service.repository;

import com.bido.bidding_service.model.Request;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RequestRepository
        extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

    @Override
    @NonNull
    @EntityGraph(attributePaths = "eventType")
    List<Request> findAll(@NonNull Specification<Request> spec);
}
