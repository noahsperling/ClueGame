package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Eric Imperio on 11/8/2016.
 */
/*Sent by player to the ClueLocalGame to show a card to the player who made a suggestion
 * Contains information about the card that is to be shown to the other player
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueShowCardAction extends ClueMoveAction implements Serializable
{
    //Long for network play
    private static final long serialVersionUID = 389087760672024L;

    /*Name of the Card that is being shown to the player who made a suggestion
     *null if the player did not have a card to show the other player
     *Note: This was made a string because the Spinners store strings*/
    private String cardToShow;

    //returns the name of the that is being shown
    public String getCardToShow()
    {
        return cardToShow;
    }

    /*Sets cardToShow to the Card that is being shown to the player who made a suggestion
    * null if the player did not have a card to show the other player
    * @param newCard;
    */
    public void setCardToShow(String newCard) {
        cardToShow = newCard;
    }


    public ClueShowCardAction(GamePlayer player){
        super(player);
        cardToShow = null; //initializes cardToShow to null
    }
}
