package com.vk.vkphoto.record;

import java.util.List;

public class ResponseRecord {

    private Integer count;
    private List<ItemRecord> items;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ItemRecord> getItems() {
        return items;
    }

    public void setItems(List<ItemRecord> items) {
        this.items = items;
    }
}