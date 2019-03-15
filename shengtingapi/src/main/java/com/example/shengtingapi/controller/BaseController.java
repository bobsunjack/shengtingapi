package com.example.shengtingapi.controller;

import com.example.shengtingapi.util.HttpClientUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class BaseController {
    public static String BASEURL = "http://41.189.38.87:30080";
    public static Integer FEATURE_VERSION = 24602;
    public static String OBJECT_TYPE = "face";
    public static String CLUSTER_ID = "1";
    public static String TEST_IMG = "D:/a.jpg";
    public static Integer PAGE_LIMIT = 1;
    public Logger logger = LoggerFactory.getLogger(getClass());

    public static String BatchDetectAndExtract =BASEURL+"/engine/image-process/face_{feature_version}/v1/batch_detect_and_extract";
   ///搜索
    public static String ClusterSearch =BASEURL+"/engine/timespace-feature/{object_type}_{feature_version}/v2/clusters/search";
///一人一档
    public static String ClusterGet =BASEURL+"/engine/timespace-feature/{object_type}_{feature_version}/v2/clusters/{cluster_id}";

    public static String SYSTEMINFO =BASEURL+"/engine/timespace-feature/{object_type}_{feature_version}/v2/get_system_info";

    public static String PORTRAIT_IMAGE_PREX = "http://41.189.38.87:30900/api/components/osg-default/_/";

    public  String realUrl(String url){
        String realUrl = url.replaceAll("\\{feature_version}", FEATURE_VERSION+"");
        logger.error("url:" + realUrl);
        return realUrl;
    }

    public  String realUrlClusterGet(String url){
        String realUrl = url.replaceAll("\\{feature_version}", FEATURE_VERSION+"");
        realUrl = realUrl.replaceAll("\\{object_type}", OBJECT_TYPE);
        realUrl = realUrl.replaceAll("\\{cluster_id}", CLUSTER_ID);
        logger.error("url:" + realUrl);
        return realUrl;
    }
}
