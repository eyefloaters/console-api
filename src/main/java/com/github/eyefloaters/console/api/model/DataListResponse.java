package com.github.eyefloaters.console.api.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class DataListResponse<T extends Resource<?>> {

    private Map<String, Object> meta;
    private Map<String, String> links;
    private final List<T> data;

    protected DataListResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void addMeta(String key, Object value) {
        if (meta == null) {
            meta = new LinkedHashMap<>();
        }
        meta.put(key, value);
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void addLink(String key, String value) {
        if (links == null) {
            links = new LinkedHashMap<>();
        }
        links.put(key, value);
    }
}
