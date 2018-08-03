/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import javax.mail.MessagingException;

/**
 *
 * @author b005
 */
interface EmailService {
    
    public void sendSimpleMessage(String to, String subject,String contents) throws MessagingException;
}
