package com.aquino.TexasWebService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SpringBootApplication
@EnableResourceServer
public class TexasWebServiceApplication {
    

	public static void main(String[] args) {
            new SpringApplicationBuilder(TexasWebServiceApplication.class)
				.properties("spring.config.name:application,config")
				.build().run(args);
	}
        
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