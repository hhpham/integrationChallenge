package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.domain.HttpResponse;
import com.hhpham.domain.event.OrderEvent;
import com.hhpham.paths.Handler;
import com.hhpham.paths.response.CreateResponse;
import com.hhpham.paths.subscription.util.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.PrintWriter;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.SUBSCRIPTION_CREATE)
public class SubscriptionCreateHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCreateHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response create(@QueryParam("url") String urlString)  {

        LOGGER.info("url received: {}", urlString);

        CreateResponse createResponse = new CreateResponse();

        if (Strings.isNullOrEmpty(urlString)) {
            createResponse.setSuccess(false);
            createResponse.setMessage("url is missing");
            return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBuilder.toXml(createResponse)).build();
        } else {

            HttpResponse httpResponse = sendRequest(urlString);

            LOGGER.info(httpResponse.toString());

            OrderEvent orderEvent = (OrderEvent)ResponseBuilder.fromXml(httpResponse.getBody());

            createResponse.setSuccess(true);
            createResponse.setMessage("Account creation successful");
            // TODO generate account id
            createResponse.setAccountIdentifier("1234");

            return Response.ok(ResponseBuilder.toXml(new CreateResponse())).build();
        }
    }
}


