/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aquino.TexasWebService.texas.cards;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Alex
 */
enum Suits {
        HEARTS(0),SPADES(1),CLUBS(3),DIAMONDS(2);
        private final int value;
        Suits(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        };
    }
enum Face {
        ACE(12),TWO(0),THREE(1),FOUR(2),FIVE(3),SIX(4),SEVEN(5),EIGHT(6), NINE(7),TEN(8),JACK(9),QUEEN(10),KING(11);
        private final int value;
        Face(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

public class Card {
    private int suit;
    private int face;
    //private final String imageName;
    
    
    public Card(Suits suit,Face face) {
        this.suit = suit.getValue();
        this.face = face.getValue();
//        imageName = 
////                "/cards/" + 
//                face.toString() + suit.toString() +".png";
    }
    @Override
    public String toString() {
        return getFace() + " of " + getSuit();
    }
    @Override
    public int hashCode() {
        return Objects.hash(getSuit(), getFace());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.getSuit() != other.getSuit()) {
            return false;
        }
        if (this.getFace() != other.getFace()) {
            return false;
        }
        return true;
    }
    
//    public String getImageName(){
//        return imageName;
//    }
    
    
    public static void main(String[] args) {
        Card card = new Card(Suits.CLUBS, Face.NINE);
//        System.out.println(new Card(Suits.CLUBS,Face.ACE));
//        System.out.println(new Card(Suits.CLUBS,Face.ACE).getImageName() );

        JFrame frame = new JFrame();
        BufferedImage img = null;
        JLabel imgLabel;
        
        File test = new File("resources/cards/FOURCLUBS.png");
        System.out.println(test.exists());
        
//        try {
//                img = ImageIO.read(
//                new File("cards/ACECLUBS.png")
//                );
//                
//            
//        } catch (Exception e) {
//        }
//        imgLabel = new JLabel(new ImageIcon(img));
//        frame.add(imgLabel);
//        frame.pack();
//        frame.setVisible(true);
        
    }

    /**
     * @return the suit
     */
    public int getSuit() {
        return suit;
    }

    /**
     * @param suit the suit to set
     */
    public void setSuit(int suit) {
        this.suit = suit;
    }

    /**
     * @return the face
     */
    public int getFace() {
        return face;
    }

    /**
     * @param face the face to set
     */
    public void setFace(int face) {
        this.face = face;
    }
    
}
