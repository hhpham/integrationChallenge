package com.hhpham.paths.subscription.response;

import com.thoughtworks.xstream.XStream;
import junit.framework.TestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateResponseTest extends TestCase {

    @Test
    public void testXmlFormat() {

        XStream xStream = new XStream();
        xStream.alias("result", CreateResponse.class);

        CreateResponse response = new CreateResponse();

        response.setSuccess(true);
        response.setMessage("account creation successful");
        response.setAccountIdentifier("1234");

        String xml = xStream.toXML(response);

        Object o = xStream.fromXML(xml);

        assertThat(o).isEqualTo(response);
    }
}