package com.example.yjy.smarthouse_android.bussiness.protocol;

/**
 * Created by yjy on 17-5-15.
 */
public class ProtocolCommandResponse {

    private Integer action;
    private Object body;

    public ProtocolCommandResponse(Integer action, Object body) {
        this.action = action;
        this.body = body;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
