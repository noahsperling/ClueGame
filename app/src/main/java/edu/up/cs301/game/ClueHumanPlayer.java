package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Noah on 11/8/2016.
 */

public class ClueHumanPlayer {

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

    public ClueHumanPlayer(String initName, int initID) {

    }

    public int getID() {
        return playerID;
    }

    public void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }

    public void recieveInfo(GameInfo i) {

    }
     public boolean supportsGui() {
         return true; //?
     }
    public boolean requiresGui() {
        return true;
    }
    public void setAsGui(GameMainActivity g) {
    }

    public ClueState getRecentState()
    {
        return recentState;
    }
    public void setAsGui(GameMainActivity g) {

    }

    public void getTopView() {

    }


    public void start() {

    }

    public void sendInfo(GameInfo g) {

    }

    public void gameSetAsGui(GameMainActivity g) {

    }

}