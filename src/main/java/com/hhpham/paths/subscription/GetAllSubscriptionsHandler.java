package com.hhpham.paths.subscription;

import com.google.common.base.Strings;
import com.hhpham.constants.Paths;
import com.hhpham.domain.HttpResponse;
import com.hhpham.domain.event.OrderEvent;
import com.hhpham.paths.Handler;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

@Path(Paths.SUBSCRIPTION_GET)
public class GetAllSubscriptionsHandler extends Handler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAllSubscriptionsHandler.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get()  {

        List<String> lines = null;
        StringBuilder sb = new StringBuilder();
        try {
            lines = Files.readAllLines(java.nio.file.Paths.get("orders.txt"),
                    Charset.defaultCharset());
            for (String line : lines) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            return Response.status(INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(sb.toString()).build();
    }
}


