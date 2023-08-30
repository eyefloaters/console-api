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

    static <V> Map<String, V> put(Map<String, V> map, String key, V value) {
        if (map == null) {
            map = new LinkedHashMap<>();
        }
        map.put(key, value);
        return map;
    }

    public List<T> getData() {
        return data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void addMeta(String key, Object value) {
        meta = put(meta, key, value);
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void addLink(String key, String value) {
        links = put(links, key, value);
    }
}
