/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.model.VerificationToken;
import com.aquino.TexasWebService.repository.TokenRepository;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author b005
 */
@Service
public class TexasTokenService {
    
    @Inject EmailService emailService;
    @Inject TokenRepository tokenRepository;

    @Value( "${domain.name}" )
    private String domainName;
    
    public User addAndSendVerification(User user) throws UnknownHostException {
        VerificationToken token = new VerificationToken(user,VerificationToken.Type.VERIFICATION);
        token = tokenRepository.save(token);
        user.setToken(token);
        //String verficationLink = servletContext.getContextPath() + "/confirm/" +user.getToken().getTokenValue();
        StringBuilder sb = new StringBuilder();
        sb.append("Click the link below to verify account: ");
        sb.append(user.getUsername());
        sb.append("\n");
        sb.append("http://");
        sb.append(domainName);
        sb.append("/confirm/");
        sb.append(user.getToken().getTokenValue());
        emailService.sendSimpleMessage(user.getEmail(), "Texas Hold'em Account Verification: " + user.getUsername(), sb.toString());
        return user;
    }
    
    public User createAndSendReset(User user) throws UnknownHostException {
        VerificationToken token = new VerificationToken(user,VerificationToken.Type.RESET);
        token = tokenRepository.save(token);
        user.setToken(token);
        StringBuilder sb = new StringBuilder();
        sb.append("Click the link below to reset password for: ");
        sb.append(user.getUsername());
        sb.append("\n");
        sb.append("http://");
        sb.append(domainName);
        sb.append("/reset/");
        sb.append(user.getToken().getTokenValue());
        emailService.sendSimpleMessage(user.getEmail(), "Texas Hold'em Password Reset: " + user.getUsername(), sb.toString());
        return user;
    }

    public VerificationToken findByTokenValue(String tokenId) {
        return tokenRepository.findByTokenValue(tokenId);
    }
    
    public VerificationToken save(VerificationToken token) {
        return tokenRepository.save(token);
    }
    
}
