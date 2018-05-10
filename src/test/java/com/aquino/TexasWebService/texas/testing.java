/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas;

import com.aquino.TexasWebService.texas.interfaces.User;

/**
 *
 * @author alex
 */
public class testing {
    public static void main(String args[]) {
//        RankPokerHandPublic.initRankPokerHand7();
//        Deck deck = Deck.getInstance();
//        User user = TexasUser.getInstance(5000);
//        for (int i = 0; i < 7; i++) {
//            user.addCard(deck.deal());
//            
//        }
        TexasGame game = TexasGame.getInstance();
        System.out.println();
        for (int i = 0; i < 4; i++) {
            game.addUser(TexasUser.getInstance(5000));
        }
        game.newRound();
//        for (User user: game.getUsers()) {
//            System.out.println(user.getTotalBet());
//        }
//        System.out.println(game.getState());
        game.bet(game.getUsers()[3], 4);
        game.bet(game.getUsers()[0], 4);
        game.addUser(TexasUser.getInstance(5000));
        game.bet(game.getUsers()[1], 3);
        game.bet(game.getUsers()[2], 2);
//        System.out.println(game.getState());
        game.bet(game.getUsers()[3], 2);
        game.bet(game.getUsers()[0], 2);
        game.bet(game.getUsers()[1], 2);
        game.bet(game.getUsers()[2], 2);
        game.bet(game.getUsers()[3], 2);
        game.bet(game.getUsers()[0], 2);
        game.bet(game.getUsers()[1], 2);
        
//        System.out.println(game.getState());
        
//        game.bet(game.getUsers()[4], 10);
        game.bet(game.getUsers()[2], 2);
//        System.out.println(game.getState());
//        for (int i = 0; i < 5; i++) {
//            for (User user : game.getUsers()) {
//                user.addCard(deck.deal());
//            
//            }
//        }
        game.bet(game.getUsers()[3], 2);
        game.bet(game.getUsers()[0], 2);
        game.bet(game.getUsers()[1], 2);
        game.bet(game.getUsers()[2], 2);
        
        
        for (User user : game.getUsers()) {
            System.out.println(user);
            System.out.printf("Money: %d%n", user.getMoney());
            System.out.println(user.getHandStrength());
            System.out.println(user.getResult());
            System.out.println();
            
            System.out.println();
           
            
        }
        game.endRound();
    }
}
        
//        
//        game.newRound();
////        for (User user: game.getUsers()) {
////            System.out.println(user.getTotalBet());
////        }
////        System.out.println(game.getState());
//        game.bet(game.getUsers()[4], 10);
//        game.bet(game.getUsers()[5], 10);
//        game.bet(game.getUsers()[0], 10);
//        game.bet(game.getUsers()[1], 10);
//        game.bet(game.getUsers()[2], 5);
//        game.bet(game.getUsers()[3], 0);
//        
//
//        
//        game.bet(game.getUsers()[4], 10);
//        game.bet(game.getUsers()[5], 10);
//        game.bet(game.getUsers()[0], 10);
//        game.foldUser(game.getUsers()[1]);
//        game.removeUser(3);
//        game.bet(game.getUsers()[2], 10);
////        game.bet(game.getUsers()[3], 10);
//        
//        game.bet(game.getUsers()[4], 10);
//        game.bet(game.getUsers()[5], 10);
////        game.bet(game.getUsers()[1], 10);
//        game.bet(game.getUsers()[0], 10);
//        game.bet(game.getUsers()[2], 10);
////        game.bet(game.getUsers()[3], 10);
//        
//        
////        for (int i = 0; i < 5; i++) {
////            for (User user : game.getUsers()) {
////                user.addCard(deck.deal());
////            
////            }
////        }
//        System.out.println(game.calculateHands());
//        System.out.println();
//        
//        for (User user : game.getUsers()) {
//            System.out.println(user);
//            System.out.printf("Money: %d%n", user.getMoney());
//            System.out.println(user.getHandStrength());
//            System.out.println(user.getResult());
//            System.out.println();
//            
//            System.out.println();
//            
//        }
//        game.endRound();
//        
//        game.newRound();
////        for (User user: game.getUsers()) {
////            System.out.println(user.getTotalBet());
////        }
////        System.out.println(game.getState());
//        game.bet(game.getUsers()[0], 10);
//        game.bet(game.getUsers()[1], 10);
//        game.bet(game.getUsers()[2], 10);
//        game.bet(game.getUsers()[3], 5);
//        game.bet(game.getUsers()[4], 0);
////        System.out.println(game.getState());
//        game.bet(game.getUsers()[0], 30);
//        game.foldUser(game.getUsers()[4]);
//        game.bet(game.getUsers()[1], 30);
//        game.bet(game.getUsers()[2], 30);
//        game.bet(game.getUsers()[3], 30);
//        
////        System.out.println(game.getState());
//        
////        game.bet(game.getUsers()[4], 10);
//        game.bet(game.getUsers()[0], 10);
//        game.bet(game.getUsers()[1], 10);
//        game.bet(game.getUsers()[2], 10);
//        game.bet(game.getUsers()[3], 10);
////        System.out.println(game.getState());
////        for (int i = 0; i < 5; i++) {
////            for (User user : game.getUsers()) {
////                user.addCard(deck.deal());
////            
////            }
////        }
//        System.out.println(game.calculateHands());
//        System.out.println();
//        
//        
//        for (User user : game.getUsers()) {
//            System.out.println(user);
//            System.out.printf("Money: %d%n", user.getMoney());
//            System.out.println(user.getHandStrength());
//            System.out.println(user.getResult());
//            System.out.println();
//            
//            System.out.println();
//           
//            
//        }
//        game.endRound();
//        
//        for (User user : game.getUsers()) {
//            System.out.println(user.getMoney());
//            
//        }
//    }
    

