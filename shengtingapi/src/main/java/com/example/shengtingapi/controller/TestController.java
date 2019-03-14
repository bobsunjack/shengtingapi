package com.example.shengtingapi.controller;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.dto.*;
import com.example.shengtingapi.util.HttpClientUtil;
import com.example.shengtingapi.util.MapUrlParamsUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/shangtang")
public class TestController extends BaseController {

    @RequestMapping(value = "/batchExtract")
    public Object batchExtract() {
        try {
            String imageStr = toImgeString();

            String param = extractParam(imageStr);
            String url = realUrl(BatchDetectAndExtract);
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    private String toImgeString() throws IOException {
        InputStream inputStream = new FileInputStream(BaseController.TEST_IMG);// 照片所在路径
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        //base64编码
        Base64 base64 = new Base64();
        return base64.encodeToString(bytes);
    }

    private String extractParam(String imageStr) {
        Map param = new HashMap();
        List data = new ArrayList<>();
        Image image = new Image();
        image.setData(imageStr);
        image.setFormat(null);
        image.setUrl(null);
        ImageExtract imageExtract = new ImageExtract("LargestFace", image);
        data.add(imageExtract);
        param.put("requests", data);
        param.put("detect_mode", "detect_mode");
        String jsonParam = JSON.toJSONString(param);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/clusterGet")
    public Object clusterGet(Integer page) {
        try {
            String param = searchGetParam(page);
            String url = realUrlClusterGet(ClusterGet);
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }
    @RequestMapping(value = "/clusterGet2")
    public Object clusterGet2(Integer page) {
        try {
            String param = searchGetParam2(page);
            String url = realUrlClusterGet(ClusterGet) + "?" + param;
            logger.error("get:"+url);
            String result = HttpClientUtil.getByUrl(url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }
    private String searchGetParam2(Integer page) {
        int offset = page*30;
        Map map = new HashMap();
        map.put("cluster_id", BaseController.CLUSTER_ID);
        map.put("period.start", "2017-01-01T10:00:20.021Z");
        map.put("period.end", "2019-10-01T10:00:20.021Z");
        map.put("page.offset", offset);
        map.put("page.limit", BaseController.PAGE_LIMIT);
        map.put("ignore_centroid", false);
        map.put("object_type", BaseController.OBJECT_TYPE);
        map.put("feature_version", BaseController.FEATURE_VERSION);
        String jsonParam = MapUrlParamsUtils.getUrlParamsByMap(map);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }

    private String searchGetParam(Integer page) {
        int offset = page;
        Period period = new Period("2017-01-01T10:00:20.021Z", "2019-10-01T10:00:20.021Z");
        Page page1 = new Page(offset, 30);
        ClusterGet clusterGet = new ClusterGet(page1, period);
        String jsonParam = JSON.toJSONString(clusterGet);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/clusterSearch")
    public Object clusterSearch(Integer top) {
        try {
            String param = clusterSearchParam(top);
            String url = realUrlClusterGet(ClusterSearch);
            String result = HttpClientUtil.postByStringJson(param, url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    private String clusterSearchParam(Integer top) throws IOException {
        String imageStr = toImgeString();
        Feature feature = new Feature(imageStr);
        Config config = new Config(top);
        ClusterSearch clusterSearch = new ClusterSearch(feature, config);
        String jsonParam = JSON.toJSONString(clusterSearch);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }


    @RequestMapping(value = "/systeminfo")
    public Object systeminfo() {
        try {
            String param = systemInfoParam();
            String url = realUrlClusterGet(SYSTEMINFO) + "?" + param;
            logger.error("get:"+url);
            String result = HttpClientUtil.getByUrl(url, null);
            logger.error("extract:" + result);
            return result;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    private String systemInfoParam() {
        Map map = new HashMap();
        map.put("object_type", BaseController.OBJECT_TYPE);
        map.put("feature_version", BaseController.FEATURE_VERSION);
        String jsonParam = MapUrlParamsUtils.getUrlParamsByMap(map);
        logger.error("param:" + jsonParam);
        return jsonParam;
    }
}
