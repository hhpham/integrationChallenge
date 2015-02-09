package com.hhpham.paths.subscription.util;

import com.hhpham.domain.event.OrderEvent;
import com.hhpham.paths.response.CreateResponse;
import com.hhpham.paths.response.Response;
import com.thoughtworks.xstream.XStream;

public class ResponseBuilder {

    private static XStream xStream;
    static {
        xStream = new XStream();
        xStream.alias("result", CreateResponse.class);
        xStream.alias("event", OrderEvent.class);
    }

    private ResponseBuilder() {
    }

    public static String toXml(Response response) {
        return xStream.toXML(response);
    }

    public static Object fromXml(String xml) {
        return xStream.fromXML(xml);
    }
}
