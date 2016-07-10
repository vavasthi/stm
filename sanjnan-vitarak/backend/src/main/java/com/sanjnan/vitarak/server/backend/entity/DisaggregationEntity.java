package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by vavasthi on 10/7/16.
 */
public class DisaggregationEntity {

    public DisaggregationEntity(Long businessId,
                                Long aggregateItemId,
                                Long aggregateItemMeasurementUnitId,
                                Long disaggregateItemId,
                                Long disAggregateItemMeasurementUnitId,
                                Float factor) {

        this.businessId = businessId;
        this.aggregateItemId = aggregateItemId;
        this.aggregateItemMeasurementUnitId = aggregateItemMeasurementUnitId;
        this.disaggregateItemId = disaggregateItemId;
        this.disAggregateItemMeasurementUnitId = disAggregateItemMeasurementUnitId;
        this.factor = factor;
    }

    public Long getAggregateItemId() {
        return aggregateItemId;
    }

    public void setAggregateItemId(Long aggregateItemId) {
        this.aggregateItemId = aggregateItemId;
    }

    public Long getAggregateItemMeasurementUnitId() {
        return aggregateItemMeasurementUnitId;
    }

    public void setAggregateItemMeasurementUnitId(Long aggregateItemMeasurementUnitId) {
        this.aggregateItemMeasurementUnitId = aggregateItemMeasurementUnitId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Long getDisaggregateItemId() {
        return disaggregateItemId;
    }

    public void setDisaggregateItemId(Long disaggregateItemId) {
        this.disaggregateItemId = disaggregateItemId;
    }

    public Long getDisAggregateItemMeasurementUnitId() {
        return disAggregateItemMeasurementUnitId;
    }

    public void setDisAggregateItemMeasurementUnitId(Long disAggregateItemMeasurementUnitId) {
        this.disAggregateItemMeasurementUnitId = disAggregateItemMeasurementUnitId;
    }

    public Float getFactor() {
        return factor;
    }

    public void setFactor(Float factor) {
        this.factor = factor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    private Long id;
    @Index
    private Long businessId;
    @Index
    private Long aggregateItemId;
    private Long aggregateItemMeasurementUnitId;
    private Long disaggregateItemId;
    private Long disAggregateItemMeasurementUnitId;
    private Float factor;
}
