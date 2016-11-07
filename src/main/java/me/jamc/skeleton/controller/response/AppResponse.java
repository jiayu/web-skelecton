package me.jamc.skeleton.controller.response;

/**
 * Created by Jamc on 11/7/16.
 */
public class AppResponse {
    private boolean success;
    private Object content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
