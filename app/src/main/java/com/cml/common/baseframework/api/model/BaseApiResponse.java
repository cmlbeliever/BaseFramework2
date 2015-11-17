package com.cml.common.baseframework.api.model;

/**
 * Created by cmlBeliever on 2015/11/17.
 */
public class BaseApiResponse {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
