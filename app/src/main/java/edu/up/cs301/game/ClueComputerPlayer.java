package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class ClueComputerPlayer extends CluePlayer {
    private int playerID;

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

    public void start() {

    }

    public void sendInfo(GameInfo g) {

    }

    public void gameSetAsGui(GameMainActivity g) {

    }
}
