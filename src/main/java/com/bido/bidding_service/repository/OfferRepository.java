package com.bido.bidding_service.repository;

import com.bido.bidding_service.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByRequestId(Long requestId);

    List<Offer> findAllBySupplierProfileId(Long supplierProfileId);

    boolean existsByRequestId(Long requestId);
}
