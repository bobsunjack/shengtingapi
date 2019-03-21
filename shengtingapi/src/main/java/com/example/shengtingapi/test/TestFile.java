package com.example.shengtingapi.test;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.json.BaseJson;
import com.example.shengtingapi.response.clusterget.ClusterGetResponse;
import com.example.shengtingapi.response.clustersearch.ClusterResponse;
import com.example.shengtingapi.response.clustersearch.ClusterSearchResponse;
import com.example.shengtingapi.response.wrap.ClusterGetItem;
import com.example.shengtingapi.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestFile {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println(DateUtil.convertToTZTime("2013-01-01 11:11:11"));
        Long time = new Date().getTime();
        System.out.println(DateUtil.convertToTZTime("2013-01-01 11:11:11"));
       // byte[] bytes = getContent("I:\\log\\search.txt");
        /*byte[] bytes = getContent("I:\\log\\s1.txt");
        String content = new String(bytes);*/
      /*  int begin = content.indexOf("blob") + 7;
        int end = content.indexOf("\"",begin) ;
        System.out.println(content.substring(begin,end));*/

     /*   byte[] bytes = getContent("I:\\log\\s1.txt");
        String content = new String(bytes);
        ClusterSearchResponse obj = JSON.parseObject(content, ClusterSearchResponse.class);
        System.out.println(obj);*/

     /*   byte[] bytes = getContent("I:\\log\\getclass.txt");
        String content = new String(bytes);
        ClusterGetResponse obj = JSON.parseObject(content, ClusterGetResponse.class);
        obj.getCluster().getResults().get(0).getObject_id().getCaptured_time_normal();//.getPortrait_image().getUrl();
        System.out.println(obj);*/
        System.out.println(DateUtil.getQtimeStrByDiffDay(-1));
        System.out.println(DateUtil.getQtimeStrByMonthLastDay(-1));

        System.out.println(DateUtil.getListTime("day",12).toString());
        System.out.println(DateUtil.getListTime("week",12).toString());
        System.out.println(DateUtil.getListTime("month",12).toString());

        ClusterGetItem item1 = new ClusterGetItem();
        item1.setCaptureTime("2019-03-01T00:45:110Z");
        ClusterGetItem item2 = new ClusterGetItem();
        item2.setCaptureTime("2019-03-01T00:46:110Z");
        List<ClusterGetItem> calclist = new ArrayList();
        calclist.add(item1);
        calclist.add(item2);
        Collections.sort(calclist);


        "182".split(",");
        BaseJson baseJson = new BaseJson();
        System.out.println(JSON.toJSONString(baseJson));
        baseJson.setBeginClusterTotal(-1L);

        if (!baseJson.getBeginClusterTotal().equals(-1)) {
          //  matchCondition.lt(baseJson.getEndClusterTotal());
        }

        Long size = new Double( Math.ceil(10 * 1.0 / 3)).longValue();
        System.out.println();

     /*   byte[] bytes = getContent("I:\\log\\search1.txt");
        String content = new String(bytes);

        ClusterSearchResponse obj = JSON.parseObject(content, ClusterSearchResponse.class);
       /// obj.getCluster().getResults().get(0).getObject_id().getCaptured_time_normal();//.getPortrait_image().getUrl();
        System.out.println(obj);*/

        byte[] bytes = getContent("I:\\log\\sysinfo.txt");
        String content = new String(bytes);

        int begin = content.indexOf("features") +11;
        int end = content.indexOf("\"",begin) ;
        String count = content.substring(begin, end);
        System.out.println(count);

    }


    public static byte[] getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return null;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        return buffer;
    }
}
