package com.hhpham.paths.response;

public class CreateResponse implements Response {

    boolean success;
    String message;
    String accountIdentifier;

    public CreateResponse() {
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

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateResponse)) return false;

        CreateResponse that = (CreateResponse) o;

        if (success != that.success) return false;
        if (accountIdentifier != null ? !accountIdentifier.equals(that.accountIdentifier) : that.accountIdentifier != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (accountIdentifier != null ? accountIdentifier.hashCode() : 0);
        return result;
    }
}
