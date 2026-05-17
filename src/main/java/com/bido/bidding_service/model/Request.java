package com.bido.bidding_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer nrPersons;

    @Column(nullable = false)
    private BigDecimal budgetTotal;

    @Column(nullable = false)
    private boolean budgetFlexible;

    @Column(nullable = false)
    private Instant eventDate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column
    private LocationCity locationCity;

    @Column
    private String locationAddress;

    @Column
    private String message;

    @Column(nullable = false)
    private boolean wantsPackage;

    @Column
    private Boolean deliveryIncluded;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.OPEN;

    @Column(nullable = false)
    private Long eventTypeId;

    @Column(nullable = false)
    private Long clientId;

    public Long getId() {
        return id;
    }

    public Integer getNrPersons() {
        return nrPersons;
    }

    public void setNrPersons(Integer nrPersons) {
        this.nrPersons = nrPersons;
    }

    public BigDecimal getBudgetTotal() {
        return budgetTotal;
    }

    public void setBudgetTotal(BigDecimal budgetTotal) {
        this.budgetTotal = budgetTotal;
    }

    public boolean isBudgetFlexible() {
        return budgetFlexible;
    }

    public void setBudgetFlexible(boolean budgetFlexible) {
        this.budgetFlexible = budgetFlexible;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public LocationCity getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(LocationCity locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isWantsPackage() {
        return wantsPackage;
    }

    public void setWantsPackage(boolean wantsPackage) {
        this.wantsPackage = wantsPackage;
    }

    public Boolean getDeliveryIncluded() {
        return deliveryIncluded;
    }

    public void setDeliveryIncluded(Boolean deliveryIncluded) {
        this.deliveryIncluded = deliveryIncluded;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(Long eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Request.class.hashCode();
    }
}
