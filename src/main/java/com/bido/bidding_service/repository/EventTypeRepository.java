package com.bido.bidding_service.repository;

import com.bido.bidding_service.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

    List<EventType> findAllByOrderByNameAsc();
}
