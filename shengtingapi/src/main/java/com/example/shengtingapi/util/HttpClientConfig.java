package com.example.shengtingapi.util;

import java.util.Date;

public class HttpClientConfig {
    public static HttpClientConfig getInstance() {
        return new HttpClientConfig();
    }

    public int connReqTimeout = 60 * 1000;
    public int connTimeout = 60 * 1000;
    public int socketTimeout = 60 * 1000;
    public int maxConnTotal = 3000;
    public int maxConnPerRoute = 1000;

    public String URL_PREFIX = "http://localhost:8080/";
    public String LOGIN_NAME = "";
    public String LOGIN_PWD = "";
    public String SESSION_ID = "";
    public Long SESSION_TIME = null;
    public String IMAGE_SERVER = null;
    public String getImageServerUrl(){
        if(IMAGE_SERVER!=null){
            return IMAGE_SERVER;
        }else{
            return URL_PREFIX;
        }
    }
    public void setSESSION_ID(String sessionId){
        SESSION_ID=sessionId;
        SESSION_TIME=new Date().getTime();
    }
    public boolean isValidSession(){
        if(SESSION_TIME==null){
            return false; ///
        }else{
           long  nowTime=new Date().getTime();
           if((nowTime-SESSION_TIME)/1000>60){ ///过期时间
               return false;
           }else{
               return true;
           }
        }
    }
    public HttpClientConfig() {
      /*  String[] params = Tools.readTxtFile(Const.SHENGTINGSERVER).split(",");
        URL_PREFIX = params[0];
        LOGIN_NAME = params[1];
        LOGIN_PWD = params[2];
        if(params.length>=4){
            IMAGE_SERVER=params[3];
        }*/
    }

    public int getConnReqTimeout() {
        return connReqTimeout;
    }

    public void setConnReqTimeout(int connReqTimeout) {
        this.connReqTimeout = connReqTimeout;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }
}