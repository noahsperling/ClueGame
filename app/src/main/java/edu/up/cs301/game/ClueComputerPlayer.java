package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class ClueComputerPlayer extends GameComputerPlayer implements CluePlayer{

    public ClueComputerPlayer(String name){
        super(name);
    }

    public int getPlayerID() {
        return playerNum;
    }

    public void setPlayerID(int newPlayerID) {
        playerNum = newPlayerID;
    }

    protected void receiveInfo(GameInfo info){}

    public boolean[] getCheckBoxArray()
    {
        return new boolean[21];
    }

    //Tag for logging
    private static final String TAG = "ClueComputerPlayer";
}
