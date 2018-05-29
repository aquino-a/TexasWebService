/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.site;

import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.model.VerificationToken;
import com.aquino.TexasWebService.repository.TokenRepository;
import com.aquino.TexasWebService.service.TexasTokenService;
import com.aquino.TexasWebService.service.UserService;
import java.time.LocalDateTime;
import javax.inject.Inject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author b005
 */
@Controller
public class UserWebController {
    
    @Inject TexasTokenService tokenService;
    @Inject UserService userService;
    
    @GetMapping("/confirm/{tokenId}")
    public String confirmUser(@PathVariable String tokenId, Model model) {
        VerificationToken token = tokenService.findByTokenValue(tokenId);
        if(token != null) {
            if(token.isVerified()) {
                model.addAttribute("verified", true);
                return "confirm";
            }
            if(token.getExpiry().isBefore(LocalDateTime.now())) {
                model.addAttribute("expired", token.getExpiry().toString());
                return "confirm";
            }
            
            
            //verify user
            token.setVerified(true);
            tokenService.save(token);
            User user = token.getUser();
            user.setEnabled(true);
            model.addAttribute("user", user);
            userService.save(user);
            return "confirm";
        }
        model.addAttribute("ok", "ok");
        return "confirm";
    }
    
    @GetMapping("/reset/{tokenId}")
    public String resetPasswordForm(@PathVariable String tokenId, Model model) {
        VerificationToken token = tokenService.findByTokenValue(tokenId);
        if(token != null) {
            if(token.isVerified()) {
                model.addAttribute("already_reset", true);
                return "reset";
            }
            if(token.getExpiry().isBefore(LocalDateTime.now())) {
                model.addAttribute("expired", token.getExpiry().toString());
                return "reset";
            }
            
            //return static reset page
            model.addAttribute("token", token.getTokenValue());
            return "reset";
        }
        model.addAttribute("ok", "ok");
        return "reset";
    }
    
    @PostMapping("/reset/{tokenId}")
    public String resetPassword(@PathVariable String tokenId, @RequestParam String[] password, Model model) {
        VerificationToken token = tokenService.findByTokenValue(tokenId);
        if(token != null) {
            if(token.isVerified()) {
                model.addAttribute("already_reset", true);
                return "reset";
            }
            if(token.getExpiry().isBefore(LocalDateTime.now())) {
                model.addAttribute("expired", token.getExpiry().toString());
                return "reset";
            }
            
            //check password
            if(!password[0].equals(password[1])) {
                model.addAttribute("matching", false);
                return "reset";
            }
                
                    
            userService.changePassword(token.getUser(),password[0]);
            
            //disable token
            token.setVerified(true);
            tokenService.save(token);
            
            //return static reset page
            model.addAttribute("just_reset", true);
            return "reset";
        }
        model.addAttribute("ok", "ok");
        return "reset";
        
    }
    
    
    
    
}
