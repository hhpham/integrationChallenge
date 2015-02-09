package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.domain.HttpResponse;
import com.hhpham.paths.subscription.response.LoginResponse;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.LOGIN)
public class LoginHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@QueryParam("url") String urlString) throws URISyntaxException, MessageException, ConsumerException {

        LOGGER.info("url received: {}", urlString);

        if (Strings.isNullOrEmpty(urlString)) {
            return Response.status(INTERNAL_SERVER_ERROR).entity("url is empty").build();
        } else {

            ConsumerManager manager = new ConsumerManager();

            // perform discovery on the user-supplied identifier
            List discoveries = null;
            try {
                discoveries = manager.discover(urlString);
            } catch (DiscoveryException e) {
                LOGGER.error("discovery exception ", e);
            }

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // store the discovery information in the user's session for later use
            // leave out for stateless operation / if there is no session
//            session.setAttribute("discovered", discovered);

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(discovered, "https://hh-integration-challenge.herokuapp.com");

            return Response.seeOther(new URI(authReq.getDestinationUrl(true))).build();
        }
    }
}
