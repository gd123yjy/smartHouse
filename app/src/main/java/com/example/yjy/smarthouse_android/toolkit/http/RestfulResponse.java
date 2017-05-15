package com.example.yjy.smarthouse_android.toolkit.http;

import com.example.yjy.smarthouse_android.exceptions.ErrorCommandException;
import com.example.yjy.smarthouse_android.exceptions.ErrorCommandResponseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yjy on 17-4-29.
 */
public class RestfulResponse {

    private JSONObject json=null;

    public RestfulResponse(Response response) throws ErrorCommandResponseException {
        ResponseBody body = response.body();
        try {
            json = new JSONObject(body.string());
        } catch (JSONException e) {
            throw new ErrorCommandResponseException();
        } catch (IOException e) {
            throw new ErrorCommandResponseException();
        }
    }

    public JSONObject getJson() {
        return json;
    }
}
