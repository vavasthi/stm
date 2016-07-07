package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class AbstractInvoice {
    public AbstractInvoice(Long businessId, Long buyerId, Long sellerId, Long userId, Boolean pricePostTax) {
        this.businessId = businessId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.transactionDate = new Date();
        this.userId = userId;
        this.pricePostTax = pricePostTax;
    }

    public AbstractInvoice() {
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getPricePostTax() {
        return pricePostTax;
    }

    public void setPricePostTax(Boolean pricePostTax) {
        this.pricePostTax = pricePostTax;
    }

    @Override
    public String toString() {
        return "AbstractInvoice{" +
                "businessId=" + businessId +
                ", id=" + id +
                ", buyerId=" + buyerId +
                ", sellerId=" + sellerId +
                ", transactionDate=" + transactionDate +
                ", userId=" + userId +
                ", pricePostTax=" + pricePostTax +
                '}';
    }

    @Id
    private Long id;
    @Id
    private Long businessId;
    @Index
    private Long buyerId;
    @Index
    private Long sellerId;
    private Date transactionDate;
    @Index
    private Long userId;
    private Boolean pricePostTax;
}