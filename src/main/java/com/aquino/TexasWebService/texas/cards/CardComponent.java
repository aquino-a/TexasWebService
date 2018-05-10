/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas.cards;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alex
 */
public class CardComponent extends Component {
    private BufferedImage img;
    private double SCALE;
    private final String SOURCEPATH = "src/com/aquino/cardgamesite/resources/cards/";
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(img,0,0,(int) (img.getWidth(null)*SCALE),(int) (img.getHeight(null)*SCALE),null);
    }
    public CardComponent(String path, double scale) {
        try {
            img = ImageIO.read(new File(SOURCEPATH +path));
            this.SCALE = scale;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }
    public CardComponent() {
        this("BACK.png", .4);
    }
    
    public void changeImage(String path, double scale) {
        try {
            img = ImageIO.read(new File(SOURCEPATH+path));
            this.SCALE = scale;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
        }
        repaint();
    }
    @Override
    public Dimension getPreferredSize() {
        if (img == null) {
            return new Dimension(200,200);
        } else {
            return new Dimension(
                     (int) (img.getWidth(null)*SCALE), (int)(img.getHeight(null)*SCALE)
            );
        }
    }
    @Override
    public int getWidth() {
        return (int) (img.getWidth(null)*SCALE);
        
    }
    @Override
    public int getHeight() {
        return (int) (img.getHeight(null)*SCALE);
    }
}
