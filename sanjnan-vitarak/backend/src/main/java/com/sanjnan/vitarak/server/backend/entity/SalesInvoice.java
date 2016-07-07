package com.sanjnan.vitarak.server.backend.entity;

import com.googlecode.objectify.annotation.Entity;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class SalesInvoice extends AbstractInvoice {

    public SalesInvoice() {
    }
    public SalesInvoice(Long businessId,
                        Long buyerId,
                        Long sellerId,
                        Long userId,
                        Boolean pricePostTax) {

        super(businessId,
                buyerId,
                sellerId,
                userId,
                pricePostTax);
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getTotalSurcharge() {
        return totalSurcharge;
    }

    public void setTotalSurcharge(float totalSurcharge) {
        this.totalSurcharge = totalSurcharge;
    }

    public float getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(float totalTax) {
        this.totalTax = totalTax;
    }

    private int numberOfItems;
    private float total;
    private float totalTax;
    private float totalSurcharge;
}