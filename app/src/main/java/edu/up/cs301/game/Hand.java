package edu.up.cs301.game;

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
        if(h != null) {
            cards = new ArrayList<Card>(h.getArrayListLength());
            Card tempCards[] = h.getCards();
            for (int i = 0; i < h.getArrayListLength(); i++) {
                cards.add(tempCards[i]);
            }
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
}