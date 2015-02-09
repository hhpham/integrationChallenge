package com.hhpham.paths.subscription.util;

import org.openid4java.association.AssociationException;
import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.*;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OpenIdConsumerManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(OpenIdConsumerManager.class);

    private static ConsumerManager manager;

    private OpenIdConsumerManager() throws ConsumerException {
    }

    public static ConsumerManager getConsumerManager() {
        if (manager == null) {
            manager = new ConsumerManager();
            manager.setAssociations(new InMemoryConsumerAssociationStore());
            manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
            manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);

            return manager;
        } else {
            return manager;
        }
    }

    public static AuthRequest getAuthRequest(String urlString, HttpServletRequest request) throws MessageException, ConsumerException {

        // perform discovery on the user-supplied identifier
        List discoveries = null;
        try {
            discoveries = getConsumerManager().discover(urlString);
        } catch (DiscoveryException e) {
            LOGGER.error("discovery exception ", e);
        }

        // attempt to associate with the OpenID provider
        // and retrieve one service endpoint for authentication
        DiscoveryInformation discovered = getConsumerManager().associate(discoveries);

        // store the discovery information in the user's session for later use
        // leave out for stateless operation / if there is no session
        HttpSession session = request.getSession();
        session.setAttribute("discovered", discovered);

        // obtain a AuthRequest message to be sent to the OpenID provider
        return getConsumerManager().authenticate(discovered, "https://hh-integration-challenge.herokuapp.com/rest/openid");
    }

    public static VerificationResult getVerificationResult(HttpServletRequest request) throws MessageException, DiscoveryException, AssociationException {
        ParameterList openidResp = new ParameterList(request.getParameterMap());

        // retrieve the previously stored discovery information
        HttpSession session = request.getSession();

        DiscoveryInformation discovered = (DiscoveryInformation) session.getAttribute("discovered");
        LOGGER.info("discovered {}", discovered);

        // extract the receiving URL from the HTTP request
        StringBuffer receivingURL = request.getRequestURL();
        final String replace = receivingURL.toString().replace("http", "https");
        receivingURL = new StringBuffer(replace);

        String queryString = request.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            receivingURL.append("?").append(request.getQueryString());
        }

        LOGGER.info("receivingURL: {}", receivingURL);
        // verify the response
        VerificationResult verification = OpenIdConsumerManager.getConsumerManager().verify(receivingURL.toString(), openidResp, discovered);

        // examine the verification result and extract the verified identifier
        return verification;
    }


}
