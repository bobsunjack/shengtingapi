package com.example.demo.dto;

public class RestResult<T> {
    public static final Integer FAIL_STATUS=-1;
    private  T result;
    private  Integer code ;///  0成功，其它失败
    private  String error ;///

    public RestResult(T result) {
        this.result = result;
        this.code = 0;
        this.error = "success";
    }

    public RestResult(T result, Integer code, String error) {
        this.result = result;
        this.code = code;
        this.error = error;
    }
    public RestResult(String error) {
        this.result = null;
        this.code = FAIL_STATUS;
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
