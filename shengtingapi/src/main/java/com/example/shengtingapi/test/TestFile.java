package com.example.shengtingapi.test;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestFile {
    public static void main(String[] args) throws IOException {
        byte[] bytes = getContent("I:\\log\\search.txt");
        String content = new String(bytes);
      /*  int begin = content.indexOf("blob") + 7;
        int end = content.indexOf("\"",begin) ;
        System.out.println(content.substring(begin,end));*/
      Object obj=  JSON.parse(content);


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
