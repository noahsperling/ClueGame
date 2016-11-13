package edu.up.cs301.game;

import java.util.ArrayList;

/**
 * Created by Noah on 11/13/2016.
 */

public class Hand {
    ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public Card[] getCards() {
        Card tempCardArray[] = new Card[cards.size()];
        for(int i = 0; i < cards.size(); i++) {
            tempCardArray[i] = cards.get(i);
        }
        return tempCardArray;
    }
}