package edu.up.cs301.game;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;


/**
 * Created by Noah on 11/13/2016.
 */

public class Hand {
    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public Hand(Hand h) {
        cards = new ArrayList<Card>(h.getArrayListLength());
        Card tempCards[] = h.getCards();
        for(int i = 0; i < h.getArrayListLength(); i++) {
            cards.add(tempCards[i]);
        }
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
    public int getArrayListLength() {
        return cards.size();
    }

    public void onDraw(Canvas c){
        Paint p = new Paint();

        for(int i=0;i<cards.size();i++) {
            p.setColor(cards.get(i).getColor());
            int width = (c.getHeight()*3/4);
            c.drawRect((width*i), 0, width*(i+1), c.getHeight(), p);
            p.setTextSize(25);
            if(Card.ROPE == cards.get(i) || Card.MRS_WHITE == cards.get(i)) {
                p.setColor(Color.BLACK);
            }else {
                p.setColor(Color.WHITE);
            }
            c.drawText(cards.get(i).getName(), (width/2)-(cards.get(i).getName().length()*5)+(width*i), c.getHeight()/2, p);
        }
    }
}