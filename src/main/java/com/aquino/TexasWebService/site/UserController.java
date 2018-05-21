/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.site;

import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.service.TexasTokenService;
import com.aquino.TexasWebService.service.UserService;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author b005
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Inject UserService userService;
    @Inject TexasTokenService tokenService;
    
    @PostMapping("/new")
    public User add(@Valid @RequestBody User user, HttpRequest request) {
        user = userService.save(user);
        tokenService.addAndSendVerification(user,request.getURI());
        return userService.save(user);
    }
    
    //securethis
    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        User user;
        try {
            user = userService.getByUsername(username);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        userService.delete(user);
        return ResponseEntity.ok().build();
    }
    
}
