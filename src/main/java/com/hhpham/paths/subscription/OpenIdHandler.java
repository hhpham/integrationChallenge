package com.hhpham.paths.subscription;

import com.hhpham.constants.Paths;
import com.hhpham.paths.subscription.util.OpenIdConsumerManager;
import com.hhpham.paths.subscription.util.ResponseBuilder;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.OPEN_ID)
public class OpenIdHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response openId(@Context HttpServletRequest request) throws URISyntaxException, AssociationException, DiscoveryException, MessageException {

//        LOGGER.info("requestURL: {}", request.getRequestURI());
        LOGGER.info("in openid");

        // extract the parameters from the authentication response
        // (which comes in as a HTTP request from the OpenID provider)
        ParameterList openidResp = new ParameterList(request.getParameterMap());

        // retrieve the previously stored discovery information
        HttpSession session = request.getSession();

        DiscoveryInformation discovered = (DiscoveryInformation) session.getAttribute("discovered");
        LOGGER.info("discovered {}", discovered);

        // extract the receiving URL from the HTTP request
//        StringBuffer receivingURL = request.getRequestURL();
        String receivingURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI() + "?" + request.getQueryString();

        LOGGER.info("receivingURL: {}", receivingURL);

//        String queryString = request.getQueryString();
//        if (queryString != null && queryString.length() > 0)
//            receivingURL.append("?").append(request.getQueryString());

        // verify the response
        VerificationResult verification = OpenIdConsumerManager.getConsumerManager().verify(receivingURL.toString(), openidResp, discovered);

        LOGGER.info("verification status {}", verification.getStatusMsg());
        LOGGER.info("verification AuthResponse {}", verification.getAuthResponse());

        // examine the verification result and extract the verified identifier
        Identifier verified = verification.getVerifiedId();

        if (verified != null) {
            LOGGER.info("verified {}", verified);
            // success, use the verified identifier to identify the user
            return Response.seeOther(new URI("https://hh-integration-challenge.herokuapp.com/")).build();
        }
        else {
            // OpenID authentication failed
            LOGGER.info("verification failed {}", verified);
            return Response.status(INTERNAL_SERVER_ERROR).build();
        }

    }
}
