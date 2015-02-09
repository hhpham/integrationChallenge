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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.xml.ws.handler.MessageContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.OPEN_ID)
public class OpenIdHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response openId(@Context HttpServletRequest request) throws URISyntaxException {

        LOGGER.info("requestURL: {}", request.getRequestURI());

        return Response.seeOther(new URI("https://hh-integration-challenge.herokuapp.com/")).build();
    }
}
