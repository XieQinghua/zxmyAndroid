package com.weishi.yiye.common.util;

import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/*****************************
 * @Copyright(c) 2014-2018
 * 长沙壹晟众美网络科技有限公司 All Rights Reserved.
 * @Author：xieqinghua
 * @Date：2018/1/15
 * @Description：基于okhttp3网络请求工具
 * @Version:v1.0.0
 *****************************/
public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();
    private static OkHttpClient client = null;

    private HttpUtils() {
    }

    public static OkHttpClient getInstance() {
        if (client == null) {
            synchronized (HttpUtils.class) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }

    /**
     * Get请求
     *
     * @param url
     * @param callback
     */
    public static void doGet(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Get请求(带参数)
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public static void doGet(String url, Map<String, Object> mapParams, Callback callback) {
        StringBuilder tempParams = new StringBuilder();
        try {
            String requestUrl;
            if (mapParams != null) {
                //处理参数
                int pos = 0;
                for (String key : mapParams.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    //对参数进行URLEncoder
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(String.valueOf(mapParams.get(key)), "utf-8")));
                    pos++;
                }
                //补全请求地址
                requestUrl = String.format("%s?%s", url, tempParams.toString());
            } else {
                requestUrl = url;
            }
            Log.e(TAG, "Get请求url = " + requestUrl);

            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();
            Call call = getInstance().newCall(request);
            call.enqueue(callback);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * Post请求发送键值对数据
     *
     * @param url
     * @param mapParams
     * @param callback
     */
    public static void doPost(String url, Map<String, String> mapParams, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            builder.add(key, mapParams.get(key));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * Post请求发送JSON数据
     *
     * @param url
     * @param jsonParams
     * @param callback
     */
    public static void doPost(String url, JSONObject jsonParams, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams.toString());
        Log.e(TAG, "Post请求url = " + url);
        Log.e(TAG, "Post请求body = " + jsonParams.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }

    /**
     * 上传文件
     *
     * @param url
     * @param pathName
     * @param fileName
     * @param callback
     */
    public static void doFile(String url, String pathName, String fileName, Callback callback) {
        //判断文件类型
        MediaType MEDIA_TYPE = MediaType.parse(judgeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(), fileName,
                        RequestBody.create(MEDIA_TYPE, new File(pathName)));
        //发出请求参数
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);

    }

    /**
     * 根据文件路径判断MediaType
     *
     * @param path
     * @return
     */
    private static String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 下载文件
     *
     * @param url
     */
    public static void downFile(String url, Callback callback) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = getInstance().newCall(request);
            call.enqueue(callback);
        } catch (Exception e) {
            //此处有可能因为url异常引起的IllegalArgumentException
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 带进度下载安装
     *
     * @param url
     * @param callback
     */
    public static void downProgressFile(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}
