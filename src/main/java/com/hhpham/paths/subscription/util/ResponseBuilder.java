package com.hhpham.paths.subscription.util;

import com.hhpham.paths.subscription.response.CreateResponse;
import com.hhpham.paths.subscription.response.Response;
import com.thoughtworks.xstream.XStream;

public class ResponseBuilder {

    private static XStream xStream;
    static {
        xStream = new XStream();
        xStream.alias("result", CreateResponse.class);
    }

    private ResponseBuilder() {
    }

    public static String build(Response response) {
        return xStream.toXML(response);
    }
}
