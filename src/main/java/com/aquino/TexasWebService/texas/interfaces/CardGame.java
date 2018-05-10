/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas.interfaces;

import java.util.List;
import java.util.Map;

/**
 *
 * @author alex
 */
public interface CardGame {
    void newRound();
    void addUser(User user);
    User newUser(int money);
    void endRound();
    User getTurn();
    User removeUser(int userId);
    User[] getUsers();
    Map<Long,User> getUserList();
    
}
