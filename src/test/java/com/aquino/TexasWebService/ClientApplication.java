/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aquino.TexasWebService;

import com.aquino.TexasWebService.site.GameMap;
import java.security.Principal;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test client for use with social application (as an OAuth2 auth server). Remember to
 * access this app via its IP address (not "localhost"), otherwise the auth server will
 * steal your cookie.
 * 
 * @author Dave Syer
 *
 */
@EnableAutoConfiguration
@Configuration
@EnableOAuth2Sso
@RestController
public class ClientApplication {
    
        
   @Inject OAuth2ClientContext clientContext;
    

    @RequestMapping("/")
    public String home(Principal user, Map<String, Object> model) {
            return "Hello " + user.getName();
    }


    @RequestMapping("/games")
    public GameMap games(Principal user, Map<String, Object> model) {
        OAuth2RestTemplate temp = restTemplate();
        System.out.println(temp.getAccessToken());
        
        System.out.println(user.getName());
        GameMap s = temp.getForObject("http://localhost:8080/games", GameMap.class);
        return s;
    }


    public static void main(String[] args) {
            new SpringApplicationBuilder(ClientApplication.class)
                            .properties("spring.config.name=client").run(args);
    }

    @Bean
    public OAuth2RestTemplate restTemplate() {
        OAuth2ProtectedResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        return restTemplate;
    }
        
        


}
