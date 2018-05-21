/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import javax.inject.Inject;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author b005
 */
@Service
public class TexasEmailService implements EmailService {
    
    @Inject
    JavaMailSender emailSender;
    
    @Override
    public void sendSimpleMessage(String to, String subject,String contents) 
            throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(contents);
        
        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw e;
        }
    }
    
}
