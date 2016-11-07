package me.jamc.skeleton.controller.response;

/**
 * Created by Jamc on 11/7/16.
 */
public class ErrorResponse {
    private boolean success;
    private String exception;
    private String errorMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
