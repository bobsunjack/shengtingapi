package com.example.shengtingapi.dto;

public class Page {
    private int offset=0;
    private int limit = 20;
    private int total ;

    public Page(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public Page() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
