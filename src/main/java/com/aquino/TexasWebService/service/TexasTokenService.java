/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.model.VerificationToken;
import com.aquino.TexasWebService.repository.TokenRepository;
import javax.inject.Inject;
import javax.servlet.ServletContext;
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
    @Inject ServletContext servletContext;
    
    public User addAndSendVerification(User user,String path) {
        VerificationToken token = new VerificationToken(user);
        token = tokenRepository.save(token);
        user.setToken(token);
        //String verficationLink = servletContext.getContextPath() + "/confirm/" +user.getToken().getTokenValue();
        StringBuilder sb = new StringBuilder();
        sb.append("Click the link below to verify account: ");
        sb.append(user.getUsername());
        sb.append("\n");
        sb.append(path);
        sb.append("/confirm/");
        sb.append(user.getToken().getTokenValue());
        emailService.sendSimpleMessage(user.getEmail(), "Texas Hold'em Account Verification: " + user.getUsername(), sb.toString());
        return user;
    }

    public VerificationToken findByTokenValue(String tokenId) {
        return tokenRepository.findByTokenValue(tokenId);
    }
    
    public VerificationToken save(VerificationToken token) {
        return tokenRepository.save(token);
    }
    
}
