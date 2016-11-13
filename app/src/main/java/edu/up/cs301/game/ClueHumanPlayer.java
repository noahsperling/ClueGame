package edu.up.cs301.game;

import android.view.View;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Noah on 11/8/2016.
 */

public class ClueHumanPlayer extends GameHumanPlayer implements GamePlayer{

    int playerID;
    String name; //I don't know if this is important or not, or even needs to be here
    private ClueState recentState;
    private boolean checkBoxBool[];
    //moveButtons
    //noteButton
    //turnButtons
    //playersHand
    //playerTurn
    //noteView

    public ClueHumanPlayer(String initName, int initID)
    {
        super(initName, initID);

    }

    @Override
    public View getTopView() {
        return null;
    }

    public int getID() {
        return playerID;
    }

    public void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }

    public void recieveInfo(GameInfo i) {
        if(i instanceof ClueState) {
            recentState = new ClueState((ClueState)i);
        }
    }

    public void setAsGui(GameMainActivity g) {
    }

    public ClueState getRecentState()
    {
        return recentState;
    }


    public void start() {

    }

    public void sendInfo(GameInfo g) {

    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

}