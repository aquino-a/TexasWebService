/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.site;

import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.service.UserService;
import com.aquino.TexasWebService.texas.TexasGame;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @author alex
 */
@RestController
@RequestMapping({"/games"})
public class GamesController {
    
    @Inject GameMap gameMap;
    @Inject UserService userService;
    
    @GetMapping(produces = {"application/JSON"})
    public GameMap getGameMap() {
        return gameMap;
    }
    
    @RequestMapping("/me")
    public Map<String, String> user(Principal principal) {
        User user = null;
        try {
            user = userService.getByUsername(principal.getName());
        } catch (UsernameNotFoundException e) {
            user = new User(principal.getName(), null, null,null,null);
            user.setEnabled(false);
            userService.save(user);
        }
        Map<String, String> map = new LinkedHashMap<>();
        map.put("name", principal.getName());
        return map;
    }
    
    @PostMapping
    public long newGame() {
        TexasGame game = TexasGame.getInstance();
        gameMap.add(game);
        return game.getGameId();
    }
    
     
}
