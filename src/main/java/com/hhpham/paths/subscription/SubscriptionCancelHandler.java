package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.domain.HttpResponse;
import com.hhpham.domain.event.OrderEvent;
import com.hhpham.paths.Handler;
import com.hhpham.paths.response.CancelResponse;
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

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.SUBSCRIPTION_CANCEL)
public class SubscriptionCancelHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCancelHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response cancel(@QueryParam("url") String urlString)  {

        LOGGER.info("url received: {}", urlString);

        CancelResponse cancelResponse = new CancelResponse();

        if (Strings.isNullOrEmpty(urlString)) {
            cancelResponse.setSuccess(false);
            cancelResponse.setMessage("url is missing");
            return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBuilder.toXml(cancelResponse)).build();
        } else {

            HttpResponse httpResponse = sendRequest(urlString);

            if(httpResponse != null) {
                LOGGER.info(httpResponse.toString());
            } else {
                cancelResponse.setSuccess(false);
                cancelResponse.setMessage("cancellation failed");
                return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBuilder.toXml(cancelResponse)).build();
            }

            // TODO assemble the cancelEvent from response

            cancelResponse.setSuccess(true);
            cancelResponse.setMessage("Account cancellation successful");

            return Response.ok(ResponseBuilder.toXml(cancelResponse)).build();
        }
    }
}


