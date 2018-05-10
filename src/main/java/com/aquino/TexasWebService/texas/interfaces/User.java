/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas.interfaces;

import com.aquino.TexasWebService.texas.cards.Card;


/**
 *
 * @author alex
 */
public interface User {
    boolean canPlay();
    boolean isPlaying();
    void fold();
    int bet(int amount);
    int getMoney();
    void addCard(Card card);
//    Card removeCard(int index);
    void discardAll();
    int getTotalBet();
    int getHandStrength();
    int getBet();
    void endRound();
    int getCardCount();
    long getUserId();
    boolean setCanPlay(int minimumBet);
    void setResult(int result);
    int getResult();
    
}
