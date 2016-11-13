package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class ClueComputerPlayer extends GameComputerPlayer implements GamePlayer{

    public ClueComputerPlayer(String name){
        super(name);
    }

    public int getID() {
        return playerID;
    }

    public void setPlayerID(int newPlayerID) {
        playerID = newPlayerID;
    }

    protected void receiveInfo(GameInfo info) {

    }

}
