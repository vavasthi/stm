package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

/**
 * Created by vavasthi on 7/7/16.
 */
public class InvoiceItem {

    public InvoiceItem(Long invoiceId,
                       Long masterItemId,
                       Long measurementUnitId,
                       Float price,
                       Float quantity,
                       Float total,
                       Float tax,
                       Float surcharge) {
        this.invoiceId = invoiceId;
        this.masterItemId = masterItemId;
        this.measurementUnitId = measurementUnitId;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.tax = tax;
        this.surcharge = surcharge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getMasterItemId() {
        return masterItemId;
    }

    public void setMasterItemId(Long masterItemId) {
        this.masterItemId = masterItemId;
    }

    public Long getMeasurementUnitId() {
        return measurementUnitId;
    }

    public void setMeasurementUnitId(Long measurementUnitId) {
        this.measurementUnitId = measurementUnitId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(Float surcharge) {
        this.surcharge = surcharge;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", masterItemId=" + masterItemId +
                ", measurementUnitId=" + measurementUnitId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", total=" + total +
                ", tax=" + tax +
                ", surcharge=" + surcharge +
                '}';
    }

    @Id
    private Long id;
    @Index
    private Long invoiceId;
    @Index
    private Long masterItemId;
    private Long measurementUnitId;
    private Float quantity;
    private Float price;
    private Float total;
    private Float tax;
    private Float surcharge;
}
