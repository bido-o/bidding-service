package com.bido.bidding_service.repository;

import com.bido.bidding_service.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestRepository
        extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {
}
