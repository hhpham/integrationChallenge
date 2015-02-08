package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.paths.subscription.response.CreateResponse;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.SUBSCRIPTION_CREATE)
public class SubscriptionCreateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCreateHandler.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(@QueryParam("url") String url) {

        LOGGER.info("url received: {}", url);

        XStream xStream = new XStream();

        CreateResponse createResponse = new CreateResponse();

        if (Strings.isNullOrEmpty(url)) {
            createResponse.setResponse("url is missing");
            return Response.status(INTERNAL_SERVER_ERROR).entity(xStream.toXML(createResponse)).build();
        } else {
            createResponse.setResponse("url is received");
            return Response.ok(xStream.toXML(createResponse)).build();
        }
    }
}


