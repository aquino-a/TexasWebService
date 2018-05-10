package com.aquino.TexasWebService;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableResourceServer
public class TexasWebServiceApplication {
    

	public static void main(String[] args) {
		SpringApplication.run(TexasWebServiceApplication.class, args);
	}
        
//        @RequestMapping("/me")
//        public Map<String, String> user(Principal principal) {
//            Map<String, String> map = new LinkedHashMap<>();
//            map.put("name", principal.getName());
//            return map;
//        }
        
        @Configuration
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
                        http.antMatcher("/games/**").authorizeRequests()
                                .anyRequest().authenticated();
//			http.antMatcher("/me").authorizeRequests().anyRequest().authenticated();
			// @formatter:on
		}
	}
        
        
}