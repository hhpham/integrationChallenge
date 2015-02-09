package com.hhpham.paths.authentication;

import com.hhpham.constants.Paths;
import com.hhpham.paths.Handler;
import com.hhpham.paths.subscription.util.OpenIdConsumerManager;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
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

        VerificationResult verificationResult = OpenIdConsumerManager.getVerificationResult(request);

        Identifier verifiedId = verificationResult.getVerifiedId();

        if (verifiedId != null) {
            LOGGER.info("verificationResult {}", verificationResult);

            // TODO retrieve exchange attributes

            // success, use the verified identifier to identify the user
            return Response.seeOther(new URI("https://hh-integration-challenge.herokuapp.com/")).build();
        }
        else {
            // OpenID authentication failed
            LOGGER.info("verification failed {}", verificationResult.getStatusMsg());
            return Response.status(INTERNAL_SERVER_ERROR).build();
        }
    }

}
