package com.khanakirana.admin.khanakirana.utils;

/**
 * Created by vavasthi on 8/10/15.
 */
public class ObjectWithStatus<T> {
    public ObjectWithStatus(T object, int status) {
        this.object = object;
        this.status = status;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private T object;
    private int status;
}
