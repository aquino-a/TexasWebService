/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.service;

import com.aquino.TexasWebService.model.Role;
import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.repository.RoleRepository;
import com.aquino.TexasWebService.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @Inject RoleRepository roleRepository;
    
    public User getByUsername(String username) throws UsernameNotFoundException{
         User user = userRepository.findByUsername(username);
         if(user == null)
             throw new UsernameNotFoundException(username);
         return user;
    }
    
    public User save(User user) {
        userRepository.save(user);
        return user;
    }
    
    public void delete(User user) {
        userRepository.delete(user);
    }
    
    public User prepareUser(User user) {
        if(user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setMoney(5000);
        user.setRoles(basicRoles());
        return user;
    }

    private List<Role> basicRoles() {
        List<Role> list = new ArrayList<>();
        list.add(roleRepository.findByName("USER"));
        list.add(roleRepository.findByName("ACTUATOR"));
        return list;
    }
    
}
