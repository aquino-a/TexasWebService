/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas;

import com.aquino.TexasWebService.texas.cards.Card;
import com.aquino.TexasWebService.texas.interfaces.Hand;
import com.aquino.TexasWebService.texas.interfaces.User;
import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 *
 * @author alex
 */
public class TexasUser implements User {
    
    @JsonIgnore
    public int betTotal;
    private int result, money,handStrength = -1, roundBet;
    private long userId;
    private String name;
    private Hand hand;
    private boolean hasEnoughMoney, playing;
    private static long userIdGenerator;
    
    private static synchronized long generateUserId() {
        return userIdGenerator++;
    }
    private TexasUser(int money, long userId) {
        this.userId = userId;
        this.money = money;
        hand = new TexasHand();
    }
    public static User getInstance(int money) {
        return new TexasUser(money, generateUserId());
    }
    
    public static User getInstanceFromKnownUser(int money, long userId, String name) {
        TexasUser user = new TexasUser(money, userId);
        user.setName(name);
        return user;
    }
    
    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public boolean canPlay() {
        return hasEnoughMoney;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void fold() {
        this.playing = false;
    }

    @Override
    public int bet(int amount) {
        betTotal += amount;
        money -= amount;
        roundBet += amount;
        return amount;
    }
 
    public void giveMoney(int amount) {
        money += amount;
    }
    
    
    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public void addCard(Card card) {
        hand.addCard(card);
    }

    @Override
    public void discardAll() {
        hand.discardAll();
    }

    @JsonIgnore
    @Override
    public int getTotalBet() {
        return betTotal;
    }
    
    @JsonIgnore
    @Override
    public int getBet() {
        return roundBet;
    }
    
    public void resetRoundBet() {
        roundBet = 0;
    }
    @JsonIgnore
    @Override
    public int getHandStrength() {
        if (handStrength == -1)
            handStrength = hand.calculateHandStrength();
        return handStrength;
    }
    
    

    @Override
    public void endRound() {
        money += getResult();
        reset();
//        result = 0;
//        roundBet = 0;
//        betTotal = 0;
//        handStrength = -1;
        hand.discardAll();
    }
    
    private void reset() {
        result = 0;
        roundBet = 0;
        betTotal = 0;
        handStrength = -1;
    }
//    private void checkMoney() {
//        if( money > 0 )
//            hasEnoughMoney = true;
//    }
    @JsonIgnore
    @Override
    public int getCardCount() {
        return hand.getCount();
    }

    @Override
    public boolean setCanPlay(int minimumBet) {
        hasEnoughMoney = money >= minimumBet;
        playing = hasEnoughMoney;
        return hasEnoughMoney;
    }

    /**
     * @return the result
     */
    @JsonIgnore
    public int getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    @Override
    public void setResult(int result) {
        this.result = result;
    }
    public boolean checkHandStrength(int maxHand) {
        return handStrength == maxHand;
    }
    
    @Override
    public String toString() {
        return "User ID: " + String.valueOf(userId);
    }
    
    @JsonIgnore
    public TexasHand getHand() {
        return (TexasHand) hand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
