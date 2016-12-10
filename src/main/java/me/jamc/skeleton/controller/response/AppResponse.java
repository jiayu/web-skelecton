package me.jamc.skeleton.controller.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jamc on 11/7/16.
 */
public class AppResponse<T> {
    private boolean success;
    private Map<String, T> body = new HashMap<String, T>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(String key, T value) {
        body.put(key, value);
    }
}
