package me.jamc.skeleton.controller.request;

/**
 * Created by Jamc on 11/7/16.
 */
public class BasicRequest {

    private String sessionId;
    private String requestId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "[ session id = " + sessionId + ", request id = " + requestId + "]";
    }
}
