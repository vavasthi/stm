package com.khanakirana.backend.entity;


import com.googlecode.objectify.annotation.Entity;

/**
 * Created by vavasthi on 7/10/15.
 */
@Entity
public class BusinessAccountResult {

    public BusinessAccountResult() {
    }

    public BusinessAccountResult(BusinessAccount account, Integer status) {
        this.account = account;
        this.status = status;
    }

    public BusinessAccount getAccount() {
        return account;
    }

    public void setAccount(BusinessAccount account) {
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

    private BusinessAccount account;
    private Integer status;
}
