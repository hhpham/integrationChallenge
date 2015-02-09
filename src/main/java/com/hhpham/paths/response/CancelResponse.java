package com.hhpham.paths.response;

public class CancelResponse implements Response {

    boolean success;
    String message;
    String errorCode;

    public CancelResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CancelResponse)) return false;

        CancelResponse that = (CancelResponse) o;

        if (success != that.success) return false;
        if (errorCode != null ? !errorCode.equals(that.errorCode) : that.errorCode != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        return result;
    }
}
