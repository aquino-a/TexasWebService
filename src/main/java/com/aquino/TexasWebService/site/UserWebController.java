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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author b005
 */
@Controller
@RequestMapping("/confirm")
public class UserWebController {
    
    @Inject TexasTokenService tokenService;
    @Inject UserService userService;
    
    @GetMapping("/{tokenId}")
    public String confirmUser(@PathVariable String tokenId, Model model) {
        VerificationToken token = tokenService.findByTokenValue(tokenId);
        if(token != null) {
            if(token.getExpiry().isBefore(LocalDateTime.now())) {
                model.addAttribute("expired", token.getExpiry().toString());
                return "confirm";
            }
            if(token.isVerified()) {
                model.addAttribute("verified", true);
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
    
}
