package com.example.yjy.smarthouse_android.toolkit.http;

import android.os.AsyncTask;

import com.example.yjy.smarthouse_android.exceptions.ErrorCommandResponseException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
    private RequestBody body = null;

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
            // to be implemented
        }
        return instance;
    }

    public static RestfulRequest create() {
        return instance;
    }

    // cannot execute network operation in UI thread
    public RestfulResponse send() {
        //发送请求获取响应
        AsyncTask<Object,Integer,Response> task = new HttpTask().execute();
        try {
            return new RestfulResponse(task.get(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (ErrorCommandResponseException e) {
            e.printStackTrace();
            return null;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return null;
        }
        // TODO: 2017/6/4 若手机无网络此处应设法产生UI提示
    }

    public RestfulRequest buildParameter(String key, String value) {
        return instance;
    }

    public RestfulRequest buildUri(String uri) {
        requestBuilder.url(uri);
        return instance;
    }

    public RestfulRequest buildOperation(String operation) {
        switch (operation){
            case "GET":
                requestBuilder.get();
                break;
            case "POST":
                requestBuilder.post(body);
                break;
            default:
                requestBuilder.method(operation,body);
                break;
        }
        return instance;
    }


    private class HttpTask extends AsyncTask<Object,Integer,Response>{

        @Override
        protected Response doInBackground(Object[] params) {
            Response response = null;
            try {
                Request request = requestBuilder.build();
                response = mOkHttpClient.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}
