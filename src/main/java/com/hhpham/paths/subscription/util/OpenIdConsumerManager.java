package com.hhpham.paths.subscription.util;

import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
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
}
