package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.domain.HttpResponse;
import com.hhpham.paths.subscription.response.LoginResponse;
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

@Path(Paths.LOGIN)
public class LoginHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginHandler.class);

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response login(@QueryParam("url") String urlString) {

        LOGGER.info("url received: {}", urlString);

        LoginResponse loginResponse = new LoginResponse();

        if (Strings.isNullOrEmpty(urlString)) {
            return Response.status(INTERNAL_SERVER_ERROR).entity("url is empty").build();
        } else {
            HttpResponse httpResponse = sendRequest(urlString);

            LOGGER.info(httpResponse.toString());

            loginResponse.setSuccess(true);

            return Response.ok(ResponseBuilder.build(loginResponse)).build();
        }
    }
}
