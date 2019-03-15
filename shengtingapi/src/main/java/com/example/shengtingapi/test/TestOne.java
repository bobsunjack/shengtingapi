package com.example.shengtingapi.test;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.controller.BaseController;
import com.example.shengtingapi.db.mongo.entity.ClusterInfo;
import com.example.shengtingapi.db.mongo.entity.ClusterStatistics;
import com.example.shengtingapi.dto.*;
import com.example.shengtingapi.util.MapUrlParamsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestOne {
    public static void main(String[] args) {
        String str = "{statisticsTime:222}";
        JSON.parseObject(str, ClusterStatistics.class);

        Map param = new HashMap();
        List data = new ArrayList<>();
        Image image = new Image();
        image.setData("data");
        image.setFormat("dd");
        image.setUrl(null);
        ImageExtract imageExtract = new ImageExtract("face_select", image);
        data.add(imageExtract);
        param.put("requests", data);
        param.put("detect_mode", "Default");

        System.out.println( JSON.toJSONString(param));

        System.out.println(BaseController.BatchDetectAndExtract.replaceAll("\\{feature_version}",BaseController.FEATURE_VERSION+""));


        Period period = new Period("2017-01-01T10:00:20.021Z", "2019-10-01T10:00:20.021Z");
        Page page1 = new Page(0,30);
        ClusterGet clusterGet = new ClusterGet(page1, period);
        String jsonParam = JSON.toJSONString(clusterGet);
        System.out.println(jsonParam);

        param = new HashMap();
        param.put("detect_mode2", "Default2");
        param.put("detect_mode", "Default3");

        System.out.println(MapUrlParamsUtils.getUrlParamsByMap(param));
    }
}
