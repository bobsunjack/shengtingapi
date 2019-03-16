package com.example.shengtingapi.test;

import com.alibaba.fastjson.JSON;
import com.example.shengtingapi.response.clusterget.ClusterGetResponse;

import java.io.*;
import java.util.ArrayList;

public class TestFile2 {
    public static void main(String[] args) throws IOException {
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
        toArrayByFileReader1("E:\\TEST\\runoob.txt");

    }


    public static ArrayList<String> toArrayByFileReader1(String name) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            //FileReader fr = new FileReader(name);
            File file = new File(name);
            InputStreamReader fr = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return  arrayList;
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
