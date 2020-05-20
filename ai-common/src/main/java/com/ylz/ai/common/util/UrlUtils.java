package com.ylz.ai.common.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description 网络工具
 * @Author haifeng.lv
 * @Date 2020/5/19 14:06
 */
public class UrlUtils {

    /**
     * @Description 通过网络地址获取文件InputStream
     * @Author haifeng.lv
     * @param: path 路径
     * @Date 2020/5/19 14:07
     * @return: java.io.InputStream
     */
    public static FileVo returnFileInfo(String path) {
        URL url = null;
        FileVo fileVo = new FileVo();

        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            // 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            fileVo.contentType = conn.getContentType();
            fileVo.inputStream = conn.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileVo;
    }

    public static class FileVo {
        private InputStream inputStream;
        private String contentType;

        public InputStream getInputStream() {
            return inputStream;
        }

        public String getContentType() {
            return contentType;
        }
    }

}
