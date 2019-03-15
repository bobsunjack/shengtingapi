package com.example.shengtingapi.response.wrap;

import java.util.List;

public class ClusterGetResult {
    private List<ClusterGetItem> items;
    private Long total;

    public ClusterGetResult(List<ClusterGetItem> items, Long total) {
        this.items = items;
        this.total = total;
    }

    public List<ClusterGetItem> getItems() {
        return items;
    }

    public void setItems(List<ClusterGetItem> items) {
        this.items = items;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
