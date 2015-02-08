package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.paths.subscription.response.CreateResponse;
import com.thoughtworks.xstream.XStream;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.glassfish.jersey.oauth1.signature.OAuth1Parameters;
import org.glassfish.jersey.oauth1.signature.OAuth1Secrets;
import org.glassfish.jersey.oauth1.signature.OAuth1Signature;
import org.glassfish.jersey.server.oauth1.internal.OAuthServerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.SUBSCRIPTION_CREATE)
public class SubscriptionCreateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCreateHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response create(@QueryParam("url") String urlString)  {

        LOGGER.info("url received: {}", urlString);

        XStream xStream = new XStream();

        CreateResponse createResponse = new CreateResponse();

        if (Strings.isNullOrEmpty(urlString)) {
            createResponse.setResponse("url is missing");
            return Response.status(INTERNAL_SERVER_ERROR).entity(xStream.toXML(createResponse)).build();
        } else {

            OAuthConsumer consumer = new DefaultOAuthConsumer("integrationchallenge-18344", "oLIbun7AxSRu");

            String result = "";
            try {
                URL url = new URL(urlString);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                consumer.sign(request);
                request.connect();

                result = "Response: " + request.getResponseCode() + " "
                        + request.getResponseMessage();

            } catch (Exception e) {
                LOGGER.error("caught exception {}e",e);
            }

            System.out.println(result);

            LOGGER.info(result);

            createResponse.setResponse("url is received");
            String response = "<result>\n" +
                    "    <success>true</success>\n" +
                    "    <message>Account creation successful</message>\n" +
                    "    <accountIdentifier>1</accountIdentifier>\n" +
                    "</result>";
            return Response.ok(response).build();
        }
    }
}


