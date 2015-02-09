package com.hhpham.constants;

public class Paths {

    private Paths() {
    }

    private static final String SUBSCRIPTION = "/subscription";

    public static final String OPEN_ID = "/openid";
    public static final String LOGIN = "/login";
    public static final String SUBSCRIPTION_CREATE = SUBSCRIPTION + "/create";
    public static final String SUBSCRIPTION_CHANGE = SUBSCRIPTION + "/change";
    public static final String SUBSCRIPTION_CANCEL = SUBSCRIPTION + "/cancel";
    public static final String SUBSCRIPTION_STATUS = SUBSCRIPTION + "/status";
    public static final String SUBSCRIPTION_GET = SUBSCRIPTION + "/get";
}
