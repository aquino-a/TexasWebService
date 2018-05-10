/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import com.aquino.TexasWebService.model.Role;
import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import javax.inject.Inject;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author alex
 */
@Service
public class UserService {
    
    @Inject UserRepository userRepository;
    @Inject PasswordEncoder passwordEncoder;
    
    public User getByUsername(String username) throws UsernameNotFoundException{
         User user = userRepository.findByUsername(username);
         if(user == null)
             throw new UsernameNotFoundException(username);
         return user;
    }
    
    public User save(User user) {
        userRepository.save(prepareUser(user));
        return user;
    }
    
    public void delete(User user) {
        userRepository.delete(user);
    }
    
    private User prepareUser(User user) {
        if(user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMoney(5000);
        user.setRoles(Arrays.asList(new Role("USER"), new Role("ACTUATOR")));
        return user;
    }
    
}
