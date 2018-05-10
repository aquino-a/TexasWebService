/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import com.aquino.TexasWebService.model.CustomUserDetails;
import com.aquino.TexasWebService.repository.UserRepository;
import javax.inject.Inject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author alex
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Inject UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return new CustomUserDetails(repository.findByUsername(username));
    }
    
}
