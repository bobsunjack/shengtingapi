package com.example.shengtingapi.controller.test;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.controller.BaseController;
import com.example.shengtingapi.dto.Image;
import com.example.shengtingapi.dto.ImageExtract;
import com.example.shengtingapi.util.HttpClientUtil;
import com.example.shengtingapi.util.MapUrlParamsUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/test")
public class ReceiveController extends BaseController {

    @RequestMapping(value = "/engine/image-process/face_1/v1/batch_detect_and_extract")
    public String batchExtract(@RequestBody String requestOnePerson) {
        try {
            System.out.println(requestOnePerson);
            return requestOnePerson;
        } catch (Exception e) {
            logger.error("", e);
        }
        return "{error:\"error\"}";
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request) {
        System.out.println(request.getParameter("test"));
        return "";
    }

    @RequestMapping(value = "/test2")
    public void test2(HttpServletRequest request) throws IOException {
        HttpClientUtil.getByUrl("http://localhost:9191/test/test?"+searchGetParam2(20),null);
    }

    @RequestMapping(value = "/test3")
    public void test3(HttpServletRequest request) throws IOException {
        String content=HttpClientUtil.getByUrl("http://www.baidu.com",null);
        System.out.println(content);
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

    }
