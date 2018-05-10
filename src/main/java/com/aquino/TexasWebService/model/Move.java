/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.model;

/**
 *
 * @author alex
 */
public class Move {
    
    private int userId;
    private int bet;
    private MoveType moveType;
    
    public enum MoveType {
        START, FOLD, BET, END;
    }
    
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * @return the bet
     */
    public int getBet() {
        return bet;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @param bet the bet to set
     */
    public void setBet(int bet) {
        this.bet = bet;
    }
    
    

    /**
     * @return the moveType
     */
    public MoveType getMoveType() {
        return moveType;
    }

    /**
     * @param moveType the moveType to set
     */
    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }
    
}
