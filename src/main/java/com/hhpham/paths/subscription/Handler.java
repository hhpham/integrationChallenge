package com.hhpham.paths.subscription;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.hhpham.domain.HttpResponse;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Handler {

    private final static Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    protected HttpResponse sendRequest(String urlString) {

        // TODO: securely load the key and secret from properties
        OAuthConsumer consumer = new DefaultOAuthConsumer("integrationchallenge-18344", "OckY0wLwtx1aiFA1");

        try {
            URL url = new URL(urlString);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            consumer.sign(request);
            request.connect();

            final InputStream inputStream = request.getInputStream();
            String content = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
            Closeables.closeQuietly(inputStream);

            HttpResponse response = new HttpResponse();

            response.setCode(request.getResponseCode());
            response.setMessage(request.getResponseMessage());
            response.setBody(content);

            request.disconnect();

            return response;

        } catch (Exception e) {
            LOGGER.error("sendRequest to url {} failed due to: {}", urlString, e);
            return null;
        }
    }
}
