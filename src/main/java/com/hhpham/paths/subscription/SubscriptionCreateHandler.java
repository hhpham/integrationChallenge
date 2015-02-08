package com.hhpham.paths.subscription;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.hhpham.constants.Paths;
import com.hhpham.paths.subscription.response.CreateResponse;
import com.hhpham.paths.subscription.util.ResponseBuilder;
import com.thoughtworks.xstream.XStream;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.SUBSCRIPTION_CREATE)
public class SubscriptionCreateHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionCreateHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response create(@QueryParam("url") String urlString)  {

        LOGGER.info("url received: {}", urlString);

        CreateResponse createResponse = new CreateResponse();

        if (Strings.isNullOrEmpty(urlString)) {
            createResponse.setSuccess(false);
            createResponse.setMessage("url is missing");
            return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBuilder.build(createResponse)).build();
        } else {

            OAuthConsumer consumer = new DefaultOAuthConsumer("integrationchallenge-18344", "OckY0wLwtx1aiFA1");

            try {
                URL url = new URL(urlString);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                consumer.sign(request);
                request.connect();

                final InputStream inputStream = request.getInputStream();
                String content = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
                Closeables.closeQuietly(inputStream);

                LOGGER.info("Response: {} {} ", request.getResponseCode(),request.getResponseMessage());
                LOGGER.info("body: {}", content);

                request.disconnect();

            } catch (Exception e) {
                LOGGER.error("caught exception {}",e);
            }

            createResponse.setSuccess(true);
            createResponse.setMessage("Account creation successful");
            // TODO generate account id
            createResponse.setAccountIdentifier("1234");

            return Response.ok(ResponseBuilder.build(createResponse)).build();
        }
    }
}


