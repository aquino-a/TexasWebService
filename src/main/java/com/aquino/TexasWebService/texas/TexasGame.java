/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas;

import com.aquino.TexasWebService.texas.interfaces.CardGame;
import com.aquino.TexasWebService.texas.interfaces.User;
import com.aquino.TexasWebService.texas.cards.Card;
import com.aquino.TexasWebService.texas.cards.Deck;
import com.aquino.TexasWebService.texas.evaluator.RankPokerHandPublic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Master version as of 3/8/18
 * @author alex
 */

public class TexasGame implements CardGame {
    
    @JsonIgnore
    private Map<Long, User> userList = new HashMap<>();
    @JsonIgnore
    private int turn, button, pot, roundBet, minBet, turnCounter;
    private long gameId, roundWinner;
    @JsonIgnore
    private TexasUser[] users, updatedUsers;
    @JsonIgnore
    private Deck deck;
//    boolean bettingRound;
    private GameState state;
    private String title;
    @JsonIgnore
    private static long gameIdGenerator;
    
    private static synchronized long generateGameId() {
        return gameIdGenerator++;
    }
    
    private TexasGame() {
        deck = Deck.getInstance();
        minBet = 2;
        state = GameState.NOROUND;
        gameId = generateGameId();
        RankPokerHandPublic.initRankPokerHand7();
    }
    public static TexasGame getInstance() {
        return new TexasGame();
    }
    
    @Override
    public void addUser(User user) {
        userList.put(user.getUserId(), user);
        updatedUsers = userList.values().stream()
                .filter(u -> u.setCanPlay(minBet))
                .toArray(TexasUser[]::new);
    }
    
    @Override
    public User removeUser(long userId) {
        
        User user = userList.get(userId);
        
        foldUser(user);
        //maybe redundant with new fold user logic
        //userCheck(user);
        userList.remove(userId);
        
        return user;
        
    }
    
    //maybe redundant
//    private void userCheck(User user) {
//        if(userList.size() == 2) {
//            for (User u : userList.values()) {
//                if(user.getUserId() != u.getUserId())
//                    ((TexasUser)user).giveMoney(pot);
//            }
//            endRound();
//        }
//    }

    @Override
    public Map<Long,User> getUserList() {
        return userList;
    }

    @Override
    public User newUser(int money) {
        return TexasUser.getInstance(money);
    }
    
    public boolean setUsers() {
        
        users = userList.values().stream()
                .filter(u -> u.setCanPlay(minBet))
                .toArray(TexasUser[]::new);
        if(users == null || users.length < 2)
            return false;
        for (TexasUser user : users) {
            user.discardAll();
        }
        return true;
    }
    //start here
    @Override
    public void newRound() {
        if(state != GameState.NOROUND)
            throw new IllegalStateException("There has to be No round already: " + state);
        if(!setUsers())
            throw new IllegalStateException("Not enough users: " + users.length);
        payBlindsAndSetTurn();
        dealToUsers();
    }
    
    private void payBlindsAndSetTurn() {
        nextState();
        turn = button;
        turn = safeIncrement(turn, 3);
        users[safeIncrement(button, 1)].bet(minBet/2);
        users[safeIncrement(button, 2)].bet(minBet);
        pot += minBet+(minBet/2);
        roundBet = minBet;
        roundWinner = -1;
        turnCounter = 0;
        System.out.println();
    }
    
    public void dealToUsers() {
        
        if(state != GameState.DEAL)
            throw new IllegalStateException("Not time for dealing: " + state);
        for (int i = 0, j = 0; i < users.length && j < 2;) {
            User user = users[i];
            user.addCard(deck.deal());
            if(i == users.length - 1 ) {
                i = 0;
                j++;
                continue;
            }
            i++;
        }
        nextState();
    }
    
    public void foldUser(User user) {
//        if(!checkUser(user))
//            throw new IllegalStateException("The user is incorrect it should be " + getTurn());
        
        user.fold();
        
        if(checkOnePlayerLeft()) {
            getLastPlayer().giveMoney(pot);
            endRound();
            return;
        }
        else if(noOnePlaying()) {
            endRound();
            return;
        }
        
        if(checkUser(user))
            setNextUser();
    }
    
    private TexasUser getLastPlayer() {
        for (TexasUser user : users) {
            if(user.isPlaying())
                return user;
        }
        return null;
    }
    
    private boolean noOnePlaying() {
        return players() == 0;
    }
    
    private int players() {
        int playingCount = 0;
        for (TexasUser user : users) {
            if(user.isPlaying())
                playingCount++;
        }
        return playingCount;
    }
    
    private boolean checkOnePlayerLeft() {
        return players() == 1;
    }
    
    public void foldUser(long userId) {
        foldUser(userList.get(userId));
    }
    
    //fix to use roundbet method
    public void bet(User user, int amount) {
       
        if(state != GameState.DEALBET 
                && state != GameState.FLOPBET 
                && state != GameState.TURNBET
                && state != GameState.RIVERBET)
            throw new IllegalStateException("It's not a time for betting: " + state);
        if(!checkUser(user))
            throw new IllegalStateException("The user is incorrect: it should be " + getTurn());
        if(!checkAmountMulitple(amount))
            throw new IllegalArgumentException("An incorrect amount was given. It must be a multiple of the minimum bet: " + amount);
        if(!checkIfEnough(user, amount))
            throw new IllegalArgumentException("The amount bet is not enough to call or raise: " + amount);
        pot += user.bet(amount);
        if(user.getBet() > roundBet) {
            roundBet = user.getBet();
            turnCounter = 0;
        }
        setNextUser();
    }
    
    public void bet(long userId, int amount) {
        bet(userList.get(userId), amount);
    }
    
    private boolean checkAmountMulitple(int amount) {
        if(state == GameState.DEALBET 
                && (amount % minBet == 0 
                || amount % (minBet/2) == 0 ))
            return true;
        else return amount % minBet == 0;
    }
    
    private boolean checkIfEnough(User user, int amount) {
        return amount >= roundBet - user.getBet();
    }
    
    private boolean checkUser(User user) {
        return user == getTurn();
    }
    
    @Override
    public User getTurn() {
        return users[turn];
    }
    
    private void setNextUser() {
        turn = safeIncrement(turn, 1);
        incrementTurnCounter();
        if(!users[turn].isPlaying())
            setNextUser();
    }
    
    private void incrementTurnCounter() {
        turnCounter++;
        if(turnCounter >= users.length) {
            turnCounter = 0;
            //roundBet = 0;
            nextState();
            if(state == GameState.FLOP)
                dealFlop();
            else if(state == GameState.TURN)
                dealTurn();
            else if(state == GameState.RIVER)
                dealRiver();
            else if(state == GameState.ENDROUND) {
                roundWinner = calculateHands().get(0).getUserId();
                waitForWinnerShow();
                endRound();
            }
        }
    }
    
    private void waitForWinnerShow() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            Logger.getLogger(TexasGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //make private
    public void dealFlop() {
        
        if(state != GameState.FLOP)
            throw new IllegalStateException("Not the time to deal the flop: " + state);
        deck.deal();
        Card first = deck.deal();
        Card second = deck.deal();
        Card third = deck.deal();
        dealCardToAllUsers(first,second,third);
        nextState();
    }
    
    public void dealTurn() {
        
        if(state != GameState.TURN)
            throw new IllegalStateException("Not the time to deal the turn card: " + state);
        deck.deal();
        Card turnCard = deck.deal();
        dealCardToAllUsers(turnCard);
        nextState();
    }
    
    public void dealRiver() {
        
        if(state != GameState.RIVER)
            throw new IllegalStateException("Not the time to deal the river card: " + state);
        deck.deal();
        Card riverCard = deck.deal();
        dealCardToAllUsers(riverCard);
        nextState();
    }
    
    private void dealCardToAllUsers(Card card) {
        for (User user : users) {
            user.addCard(card);
        }
    }
    
    private void dealCardToAllUsers(Card ... cards) {
        for (User user : users) {
            for(Card card : cards) {
                user.addCard(card);
            }
        }
    }
//    private void dealCardToAllUsers(Card ... card) {
//        for (Card card1 : card) {
//            dealCardToAllUsers(card1);
//        }
//    }
    public void setMinimumBet(int amount) {
        if(amount % 2 != 0 && amount < 2)
            throw new IllegalArgumentException("Minimum bet must be even: " + amount);
        minBet = amount;
    }
    
    @JsonIgnore
    public int getMinimumBet() {
        return minBet;
    }
    
//    private void safeIncrementTurn() {
//        if(turn == users.length - 1)
//            turn = 0;
//        else turn++;
//    }
//    private void safeIncrementButton() {
//        if(button == users.length - 1)
//            button = 0;
//        else button++;
//    }
    //change to private
    public int safeIncrement(int value, int howManyTimes) {
        for (int i = 0; i < howManyTimes; i++) {
            if(value >= users.length - 1)
                value = 0;
            else value++;
        }
        return value;
    }
    
    
    public List<TexasUser> calculateHands() {
        int minimumStack, potCalculate = 0;
        List<TexasUser> winners = new ArrayList<>();
        List<TexasUser> list = new ArrayList(Arrays.asList(users));
        for (User user : list) {
            user.getHandStrength();
//            user.setResult(-user.getTotalBet());
        }
        while(list.size() > 1) {
            
            minimumStack = list.stream()
                    .mapToInt(User::getTotalBet)
                    .min().getAsInt();
            potCalculate += minimumStack*list.size();
            for (TexasUser user : list) {
                 user.betTotal -= minimumStack;
            }
//            final int maxHand = list.stream()
//                    .filter(User::isPlaying)
//                    .mapToInt(User::getHandStrength)
//                    .max().getAsInt();
//            winners = list.stream()
//                    .filter(User::isPlaying)
//                    .filter(u -> u.getHandStrength() == maxHand)
//                    .collect(Collectors.toList());
            winners = list.stream()
                    .collect(Collectors.groupingBy(User::getHandStrength,TreeMap::new, Collectors.toList()))
                    .lastEntry()
                    .getValue();
            for (TexasUser winner : winners) {
                winner.setResult(winner.getResult()+(potCalculate/winners.size()));
            }
            list = list.stream()
                    .filter(u -> u.betTotal > 0)
                    .collect(Collectors.toList());
            potCalculate =0;
        }
        if (list.size() == 1) {
            User user = list.get(0);
            user.setResult(user.getResult()+ user.getTotalBet());
        }
        System.out.println(winners.get(0));
        
        return winners;
    }

    @Override
    public void endRound() {
        pot = 0;
        turnCounter = 0;
        deck = Deck.getInstance();
        button = safeIncrement(button, 1);
        for (TexasUser user : users) {
            user.endRound();
        }
        state = GameState.NOROUND;
    }
    
    private void nextState() {
        state = state.next();
    }
    public enum GameState {
        
        NOROUND,DEAL,DEALBET,FLOP,FLOPBET,TURN,TURNBET,RIVER,RIVERBET,ENDROUND {
            @Override
            GameState next() {
                return values()[0];
            }
        };
        
        GameState next() {
            return values()[ordinal()+1];
        }
    }
    
    /**
     * @return the state
     * delete this
     */
    public GameState getState() {
        return state;
    }
    
    @JsonIgnore
    @Override
    public User[] getUsers() {
        return users;
    }
    
    @JsonIgnore
    public User getUser(long userId) {
        return userList.get(userId);
    }
    
    @JsonIgnore
    public User[] getRoomUsers() {
        return userList.values().toArray(new TexasUser[0]);
    }
    
    @JsonIgnore
    public int getUserCount() {
        return userList.size();
    }
    
    @JsonIgnore
    public int getTotalPot() {
        return pot;
    }
    
    @JsonIgnore
    public int getAmountToCall(int userId) {
        return getAmountToCall(userList.get(userId));
    }
    
    @JsonIgnore
    public int getAmountToCall(User user) {
        return roundBet - user.getBet();
    }
    
    @JsonIgnore
    public long getButtonUserId() {
        return users[button].getUserId();
    }
    
    public long getGameId() {
        return gameId;
    }
    
    public long getRoundWinner() {
        return roundWinner;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
}
