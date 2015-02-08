package com.hhpham;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OAuthTest {

    @Test
    public void test() throws IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {
        OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        URL url = new URL("https://www.appdirect.com/AppDirect/rest/api/events/dummyChange");
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        consumer.sign(request);
        request.connect();
    }
}
