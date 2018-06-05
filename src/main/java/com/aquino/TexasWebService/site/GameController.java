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
    public String join(@PathVariable long id, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        gameMap.getGame(id).addUser(
                TexasUser.getInstanceFromKnownUser(
                        user.getMoney(),user.getId(),user.getUsername()));
        
        return "Your userId for this game is: " + user.getId();
    }
    
    @PostMapping("/leave")
    public int leave(@PathVariable int id, Principal principal ) {
        User user = userService.getByUsername(principal.getName());
        com.aquino.TexasWebService.texas.interfaces.User texasUser = 
                gameMap.getGame(id).removeUser(user.getId());
        user.setMoney(texasUser.getMoney());
        userService.save(user);

        return user.getMoney();
    }
    
    @GetMapping(params={"userId"})
    public GameState getState(@PathVariable int id, @RequestParam int userId) {
        return GameState.getGameState(gameMap.getGame(id), userId);
    }
    
    @PostMapping("/move")
    public GameState receiveMove(@RequestBody Move move, @PathVariable int id) {
        TexasGame game = gameMap.getGame(id);
        processMove(game, move);
        return GameState.getGameState(game, move.getUserId());
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
    

