package com.example.yjy.smarthouse_android.toolkit.http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yjy on 17-4-29.
 * here is a simple restful client implemented by HTTPClient or OKHTTP
 */

public class RestfulRequest {
    private static final String TAG = "RestfulRequest";

    public static RestfulRequest instance = new RestfulRequest();
    public static OkHttpClient mOkHttpClient = new OkHttpClient();

    private Request.Builder requestBuilder;
    private RequestBody body;

    private RestfulRequest(){
        requestBuilder = new Request.Builder();
    }

    public RestfulRequest buildHeader(String key,String value) {
        requestBuilder.header(key,value);
        return instance;
    }

    public RestfulRequest buildContent(Object message) {
        if (message instanceof JSONObject){
            body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),message.toString());
        }else {
            // TODO: 17-5-1  to be implemented
        }
        return instance;
    }

    public static RestfulRequest create() {
        return instance;
    }

    // cannot execute network operation in UI thread
    public RestfulResponse send() {
        //发送请求获取响应
        new HttpTask().execute();
        return new RestfulResponse();
    }

    public RestfulRequest buildParameter(String key, String value) {
        return instance;
    }

    public RestfulRequest buildUri(String uri) {
        requestBuilder.url(uri);
        return instance;
    }

    public RestfulRequest buildOperation(String operation) {
        requestBuilder.method(operation,body);
        return instance;
    }


    private class HttpTask extends AsyncTask<Object,Object,Object>{

        @Override
        protected Object doInBackground(Object[] params) {
            Response response = null;
            try {
                Request request = requestBuilder.build();
                response = mOkHttpClient.newCall(request).execute();
                // discard below code if the network is poor
                //判断请求是否成功
                if(response.isSuccessful()){
                    //打印服务端返回结果
                    Log.i(TAG,response.body().string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}
