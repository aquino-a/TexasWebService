/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.model;

import com.aquino.TexasWebService.service.UserService;
import com.aquino.TexasWebService.texas.TexasGame;
import com.aquino.TexasWebService.texas.TexasUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author alex
 */
public class GameState {
    
    @Autowired
    UserService userService;
    
    public final long userId,turnUserId,buttonUserId,roundWinner;
    public final int totalPot,amountToCall,minBet;
    public final User[] users;
    public final int[] cards;
    public final com.aquino.TexasWebService.texas.TexasGame.GameState state;

    private GameState(long userId, long turnUserId,
            long buttonUserId, User[] users, int totalPot,
            int amountToCall, int minBet, int[] cards,
            com.aquino.TexasWebService.texas.TexasGame.GameState state,
            long roundWinner) {
        
        this.userId = userId;
        this.turnUserId = turnUserId;
        this.buttonUserId = buttonUserId;
        this.users = users;
        this.totalPot = totalPot;
        this.amountToCall = amountToCall;
        this.minBet = minBet;
        this.cards = cards;
        this.state = state;
        this.roundWinner = roundWinner;
    }
    
    public static GameState getGameState(TexasGame game, long userId, User[] users) {
        TexasUser user = (TexasUser) game.getUser(userId);
        if(game.getState() == TexasGame.GameState.NOROUND ||
                game.getState() == TexasGame.GameState.ENDROUND) {
            return new GameState(userId, -1,
                -1, users,
                game.getTotalPot(), game.getAmountToCall(user),
                game.getMinimumBet(), user.getHand().getCards(),game.getState(),
                game.getRoundWinner());
        }
       
        return new GameState(userId, game.getTurn().getUserId(),
                game.getButtonUserId(), users,
                game.getTotalPot(), game.getAmountToCall(user),
                game.getMinimumBet(), user.getHand().getCards(),game.getState(),
                game.getRoundWinner());
    }
    
}
