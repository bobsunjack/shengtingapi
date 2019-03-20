package com.example.shengtingapi.test;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.util.DateUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestTwo {
    public static void main(String[] args) {
        Map param = new LinkedHashMap();
        param.put("a", "sdfsdfl");
        param.put("c", "sdfsdfl");
        param.put("b", "sdfsdfl");

        System.out.println(DateUtil.shangTangTimeToStr("2019-03-12T06:58:41.735Z"));

        System.out.println(JSON.toJSONString(param));
    }
}
