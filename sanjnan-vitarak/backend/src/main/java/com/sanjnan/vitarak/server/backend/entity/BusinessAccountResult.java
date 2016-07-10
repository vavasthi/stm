package com.sanjnan.vitarak.server.backend.entity;


import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 7/10/15.
 */
@Entity
public class BusinessAccountResult {

    public BusinessAccountResult() {
    }

    public BusinessAccountResult(BusinessAdminAccount account, Integer status) {
        this.account = account;
        this.status = status;
    }

    public BusinessAdminAccount getAccount() {
        return account;
    }

    public void setAccount(BusinessAdminAccount account) {
        this.account = account;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BusinessAccountResult{" +
                "account=" + account +
                ", status=" + status +
                '}';
    }

    private BusinessAdminAccount account;
    private Integer status;
}
