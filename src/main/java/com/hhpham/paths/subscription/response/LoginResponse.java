package com.hhpham.paths.subscription.response;

public class LoginResponse implements Response {

    boolean success;

    public LoginResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
