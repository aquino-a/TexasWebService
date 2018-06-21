/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.site;

import com.aquino.TexasWebService.model.GameState;
import com.aquino.TexasWebService.model.Move;
import com.aquino.TexasWebService.model.User;
import com.aquino.TexasWebService.service.UserService;
import com.aquino.TexasWebService.texas.TexasGame;
import com.aquino.TexasWebService.texas.TexasUser;
import java.security.Principal;
import javax.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alex
 */
@RestController
@RequestMapping("/games/{id}")
public class GameController {
    
    @Inject GameMap gameMap;
    @Inject UserService userService;
    
    @PostMapping("/join")
    public ResponseEntity<String> join(@PathVariable long id, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        TexasGame game = gameMap.getGame(id);
        if(game.getUserList().containsKey(user.getId()))
            return ResponseEntity.badRequest().body(null);
        game.addUser(
                TexasUser.getInstanceFromKnownUser(
                        user.getMoney(),user.getId(),user.getUsername()));
        return ResponseEntity.ok(
                "Your userId for this game is: " + user.getId());
    }
    
    @PostMapping("/leave")
    public int leave(@PathVariable int id, Principal principal ) {
        User user = userService.getByUsername(principal.getName());
        TexasGame game = gameMap.getGame(id);
        com.aquino.TexasWebService.texas.interfaces.User texasUser = 
                game.removeUser(user.getId());
        user.setMoney(texasUser.getMoney());
        userService.save(user);
        
        if(game.getUserList().isEmpty())
            gameMap.remove(id);

        return user.getMoney();
    }
    
    @GetMapping()
    public GameState getState(@PathVariable int id, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        TexasGame game = gameMap.getGame(id);
        return GameState.getGameState(
                game, user.getId(),realUsers((TexasUser[]) game.getRoomUsers()));
    }
    
    private User[] realUsers(TexasUser[] users) {
        User[] actualUsers = new User[users.length];
        for (int i = 0; i < users.length; i++) {
            TexasUser user = users[i];
            User actualUser = userService.findById(user.getUserId());
            actualUser.setMoney(user.getMoney());
            actualUsers[i] = actualUser;
        }
        return actualUsers;
    }
    
    @PostMapping("/move")
    public GameState receiveMove(@RequestBody Move move,
            @PathVariable int id,Principal principal) {
        move.setUserId(userService.getByUsername(principal.getName()).getId());
        TexasGame game = gameMap.getGame(id);
        processMove(game, move);
        return GameState.getGameState(
                game, move.getUserId(),realUsers((TexasUser[]) game.getRoomUsers()));
    }
    
//    @PostMapping(produces = {"application/JSON"})
//    public GameState sendState() {
//        
//    }

    private void processMove(TexasGame game , Move move) {
        Move.MoveType type = move.getMoveType();
        if(type == Move.MoveType.START)
            game.newRound();
        if(type == Move.MoveType.FOLD)
            game.foldUser(move.getUserId());
        if(type == Move.MoveType.BET)
            game.bet(move.getUserId(), move.getBet());
        if(type == Move.MoveType.END)
            game.endRound();
    }
    
    
}
    

