/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas.cards;

import com.aquino.TexasWebService.texas.TexasHand;
import com.aquino.TexasWebService.texas.evaluator.RankPokerHandPublic;
/**
 *
 * @author Alex
 */
public class Deck {
    private final Card[] deck;
    private int count = -1;
    
    private Deck() {
        deck = new Card[52];
        initializeDeck();
        shuffle();
    }
    public static Deck getInstance() {
        return new Deck();
    }
    
    private void initializeDeck() {
        for (Suits suit : Suits.values()) {
            for (Face face : Face.values()) {
                //count++;
                deck[++count] = new Card(suit, face);
                //if(count < 51)
                
            }
        }
    }
    public void printCards() {
        for (Card card : deck) {
            System.out.println(card);
        }
    }
    public Card deal() throws IllegalStateException {
        if(count <= -1) throw new IllegalStateException("There are no cards.");
        Card temp = deck[count];
        deck[count--] = null;
        return temp;
    }
    public void shuffle() {
        Card temp;
        int num;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < (deck.length); j++) {
                num = (int) (Math.random()*(deck.length));
                temp = deck[j];
                deck[j] = deck[num];
                deck[num] = temp;
            }
        }
    }
    public int getCount() {
        return count;
    }
    
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();
        TexasHand hand1 = new TexasHand();
        TexasHand hand2 = new TexasHand();
        RankPokerHandPublic.initRankPokerHand7();
//        Card[] hand1 = new Card[7];
//        Card[] hand2 = new Card[7];
        for(int j = 0, d = 0; j < 100; j++, d++) {
            
            if ( d  == 2) {
                deck = new Deck();
                deck.shuffle();
                d = 0;
            }
                
            
            System.out.printf("Trial #%d: %n", j);
            hand1 = new TexasHand();
            hand2 = new TexasHand();
            for (int i = 0; i < 7; i++) hand1.addCard(deck.deal());
            for (int i = 0; i < 7; i++) hand2.addCard(deck.deal());
            System.out.println(RankPokerHandPublic.Combination.fromRank(RankPokerHandPublic.rankPokerHand7(hand1.getFaces(), hand1.getSuits()) >> 26));
            System.out.println(RankPokerHandPublic.Combination.fromRank(RankPokerHandPublic.rankPokerHand7(hand2.getFaces(), hand2.getSuits()) >> 26));
        }
        
        
//        for (int i = 0; i < 51; i++) {
//            
//            System.out.println(deck.deal());
//            //deck.shuffle();
//        }

    }

    /**
     * @return the count
     */
    
    
}

