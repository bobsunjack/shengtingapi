package com.example.shengtingapi.test;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.response.clusterget.ClusterGetResponse;
import com.example.shengtingapi.response.clustersearch.ClusterResponse;
import com.example.shengtingapi.response.clustersearch.ClusterSearchResponse;
import com.example.shengtingapi.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestFile {
    public static void main(String[] args) throws IOException {
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


        byte[] bytes = getContent("I:\\log\\search1.txt");
        String content = new String(bytes);
        ClusterSearchResponse obj = JSON.parseObject(content, ClusterSearchResponse.class);
       /// obj.getCluster().getResults().get(0).getObject_id().getCaptured_time_normal();//.getPortrait_image().getUrl();
        System.out.println(obj);
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
