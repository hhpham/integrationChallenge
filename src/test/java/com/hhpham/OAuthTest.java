package com.hhpham;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OAuthTest {

    @Test
    public void test() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        OAuthConsumer consumer = new DefaultOAuthConsumer("integrationchallenge-18344", "OckY0wLwtx1aiFA1");
        URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/dummyChange");
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        consumer.sign(request);
        request.connect();

        final InputStream inputStream = request.getInputStream();
        String content = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
        Closeables.closeQuietly(inputStream);

        System.out.println("Response: " + request.getResponseCode() + " "
                + request.getResponseMessage());

        System.out.println("body: " + content);

        request.disconnect();
    }
}
