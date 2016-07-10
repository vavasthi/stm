package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 19/9/15.
 */
@Entity
public class MasterStockEntity {
    public MasterStockEntity(Long businessId, Long itemId, Long measurementUnitId, Float stock) {
        this.businessId = businessId;
        this.itemId = itemId;
        this.measurementUnitId = measurementUnitId;
        this.stock = stock;
    }

    public MasterStockEntity() {
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(Long measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    public Float getStock() {
        return stock;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    @Id
    private Long id;
    @Index
    private Long businessId;
    @Index
    private Long itemId;
    @Index
    private Long measurementUnitId;
    private Float stock;
}
