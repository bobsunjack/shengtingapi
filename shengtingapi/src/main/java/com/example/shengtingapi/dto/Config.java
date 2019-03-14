package com.example.shengtingapi.dto;

public class Config {
    private Float min_score = 0.0f;
    private Integer top_k;
    private Period period;

    public Config(Integer top_k) {
        this.top_k = top_k;
    }

    public Float getMin_score() {
        return min_score;
    }

    public void setMin_score(Float min_score) {
        this.min_score = min_score;
    }

    public Integer getTop_k() {
        return top_k;
    }

    public void setTop_k(Integer top_k) {
        this.top_k = top_k;
    }



    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
