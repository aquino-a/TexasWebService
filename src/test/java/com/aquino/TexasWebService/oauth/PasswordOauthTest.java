/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author b005
 */
public class PasswordOauthTest {
    
    String location = "http://localhost/oauth/token";
    URL url;
    String credentials64 = new String(Base64.getEncoder().encode("acme:secret".getBytes()));
    String userpass = new String(Base64.getEncoder().encode("john:pass".getBytes()));

    public PasswordOauthTest() throws URISyntaxException, MalformedURLException {
        this.url = new URIBuilder(location)
               .addParameter("grant_type", "password")
                .addParameter("username","john")
                .addParameter("password", "pass")
                .addParameter("scope","read")
                
                
                .build().toURL();
    }
    
    public void connect() {
        try {
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic " + credentials64);
            //con.setRequestProperty("grant_type", "password");
//            con.setRequestProperty("username", "john");
//            con.setRequestProperty("password", "pass");
            con.connect();
            if(con.getResponseCode() == 200)
                printResponse(con.getInputStream());
            else System.out.println("NOT 200");
        } catch (IOException ex) {
            Logger.getLogger(PasswordOauthTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public static void main(String[] args) {
        try {
            PasswordOauthTest p = new PasswordOauthTest();
            p.connect();
        } catch (URISyntaxException ex) {
            Logger.getLogger(PasswordOauthTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(PasswordOauthTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printResponse(InputStream inputStream) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            for(String line = br.readLine(); line != null; line = br.readLine())
                System.out.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            
    
}
