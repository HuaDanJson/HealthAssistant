package com.demo.bs.demoapp2.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/6.
 */
public abstract class HttpUtil {
    private String urlpath ="http://172.16.43.54:8080/TInfoWebServer/servlet/";
    private Context context;
    private Map<String,Object> paramsmap =new HashMap<>();
    protected abstract void onCallback(String json);
    private AsyncTask myhttp;
    private String httpurl;
    //HttpUtil的构造函数
    public HttpUtil(Context context, String path) {
        this.context = context;
        httpurl =path;
    }
    public void addParams (String key, String value){
        paramsmap.put(key,value);
    }
    public void sendGetRequest() {
       myhttp = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    return getRequest(httpurl, paramsmap);
                } catch (Exception e) {
                    return "error";
                }
            }
            @Override
            protected void onPostExecute(String s) {
                onCallback(s);
            }


       }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }
    private String getRequest(String path, Map<String,Object> params)throws IOException {
        StringBuilder url = new StringBuilder(urlpath+path);
        url.append("?");
        for (Map.Entry<String,Object> entity : params.entrySet()) {
            String getkey = URLEncoder.encode(entity.getKey(), "utf-8");
            String getvalue =  URLEncoder.encode((String) entity.getValue(), "utf-8");
            url.append(getkey).append("=");
            url.append(getvalue);
            url.append("&");
        }
        url.deleteCharAt(url.length() - 1);
        Log.d("zjw", url + "");
        HttpURLConnection conn = (HttpURLConnection) new URL(url.toString())
                .openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {

            InputStream in = conn.getInputStream();
            int len;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = in.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            String s = bos.toString();

            Log.d("http Response","------------");
            Log.d("http Response",s);
            Log.d("http Response","------------");
            return s;
        } else {
            return "error";
        }
    }

}
