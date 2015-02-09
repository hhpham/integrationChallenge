package com.hhpham.paths.subscription;

import com.hhpham.constants.Paths;
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

@Path(Paths.OPEN_ID)
public class OpenIdHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response openId() throws URISyntaxException {

//        LOGGER.info("requestURL: {}", request.getRequestURI());

        return Response.seeOther(new URI("https://hh-integration-challenge.herokuapp.com/")).build();
    }
}
