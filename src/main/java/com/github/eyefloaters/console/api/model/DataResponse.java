package com.github.eyefloaters.console.api.model;

public abstract class DataResponse<T extends Resource<?>> {

    private final T data;

    protected DataResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
