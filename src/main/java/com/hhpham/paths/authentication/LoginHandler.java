package com.hhpham.paths.authentication;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.paths.Handler;
import com.hhpham.paths.subscription.util.OpenIdConsumerManager;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.LOGIN)
public class LoginHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@QueryParam("url") String urlString, @Context HttpServletRequest request) throws URISyntaxException, MessageException, ConsumerException {

        LOGGER.info("url received: {}", urlString);

        if (Strings.isNullOrEmpty(urlString)) {
            return Response.status(INTERNAL_SERVER_ERROR).entity("url is empty").build();
        } else {

            AuthRequest authReq = OpenIdConsumerManager.getAuthRequest(urlString, request);

            // TODO: research attribute exchange with appDirect
//            // Attribute Exchange example: fetching the 'email' attribute
//            FetchRequest fetch = FetchRequest.createFetchRequest();
//            fetch.addAttribute("email", "http://schema.openid.net/contact/email", true);
//            fetch.addAttribute("fullname", "http://schema.openid.net/namePerson", true);
//
//            // attach the extension to the authentication request
//            authReq.addExtension(fetch);

            String destinationUrl = authReq.getDestinationUrl(true);
            LOGGER.info("destinationUrl {}", destinationUrl);
            return Response.seeOther(new URI(destinationUrl)).build();
        }
    }
}
