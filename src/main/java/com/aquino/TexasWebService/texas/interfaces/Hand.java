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
public interface Hand {
    void addCard(Card card);
    default void removeCard(int index) {};
    void discardAll();
    int getCount();
    int calculateHandStrength();
}
