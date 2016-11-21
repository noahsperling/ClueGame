package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by Noah on 11/8/2016.
 */

public class ComputerPlayerSmart extends GameComputerPlayer {
    //does smart AI stuff

    public ComputerPlayerSmart(String name){
        super(name);
    }

    protected void receiveInfo(GameInfo info) {

    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }
}
