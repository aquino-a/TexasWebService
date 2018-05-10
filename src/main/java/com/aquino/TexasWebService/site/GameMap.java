/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.site;

import com.aquino.TexasWebService.texas.TexasGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 *
 * @author alex
 */
@Component
public class GameMap {
    
    private Map<Long,TexasGame> gameMap = new HashMap<>();
    
    public Map<Long,TexasGame> getList() {
        return Collections.unmodifiableMap(gameMap);
    }
    
    public void add(TexasGame game) {
        gameMap.put(game.getGameId(), game);
    }
    
    public void remove(long gameId) {
        gameMap.remove(gameId);
    }
    
    public int getSize() {
        return gameMap.size();
    }
    
    public TexasGame getGame(long gameId) {
        return gameMap.get(gameId);
    }
}
