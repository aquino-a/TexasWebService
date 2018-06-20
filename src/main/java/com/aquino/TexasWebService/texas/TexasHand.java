/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas;

import com.aquino.TexasWebService.texas.cards.Card;
import com.aquino.TexasWebService.texas.evaluator.RankPokerHandPublic;
import com.aquino.TexasWebService.texas.interfaces.Hand;


/**
 *
 * @author alex
 */
public class TexasHand implements Hand {
    
    private int[] faces;
    private int[] suits;
    int count;

    public TexasHand() {
        discardAll();
    }

    @Override
    public void addCard(Card card) {
        if(count >= 7)
            throw new IllegalStateException("too many cards in the Hand.");
        faces[count] = card.getFace();
        suits[count++] = card.getSuit();
    }

    /**
     * @return the faces
     */

    public int[] getFaces() {
        return faces;
    }

    /**
     * @param faces the faces to set
     */
    public void setFaces(int[] faces) {
        this.faces = faces;
    }

    /**
     * @return the suits
     */

    public int[] getSuits() {
        return suits;
    }

    /**
     * @param suits the suits to set
     */
    public void setSuits(int[] suits) {
        this.suits = suits;
    }
    
    public int[] getCards() {
        int[] cards = new int[7];
        for (int i = 0; i < 7; i++) {
            if(suits[i] == -1) {
                cards[i] = 0;
                continue;
            }
                
            cards[i] = ((suits[i]+1)*100) + ((faces[i]+1));
        }
        return cards;
    }
    // add get from int for faces and suits
//    @Override
//    public void removeCard(int index) {
//        faces[index] = -1;
//        suits[index] = -1;
//    }

    @Override
    public void discardAll() {
        faces = new int[]{-1,-1,-1,-1,-1,-1,-1};
        suits = new int[]{-1,-1,-1,-1,-1,-1,-1};
        count = 0;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int calculateHandStrength() {
        if(count != 7)
            throw new IllegalStateException(
                    "Not the right amount of cards: " + count);
        return RankPokerHandPublic.rankPokerHand7(faces, suits);
    }
        
}
    

