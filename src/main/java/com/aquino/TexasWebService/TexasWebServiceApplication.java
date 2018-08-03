package com.aquino.TexasWebService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@SpringBootApplication
@EnableResourceServer
public class TexasWebServiceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TexasWebServiceApplication.class).properties("spring.config.name:application,config");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(TexasWebServiceApplication.class, args);
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
