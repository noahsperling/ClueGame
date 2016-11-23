package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class ClueComputerPlayer extends GameComputerPlayer implements GamePlayer, CluePlayer{

    private boolean checkBoxBool[];

    public ClueComputerPlayer(String name){
        super(name);

        //Set boolean array to false initially!  When they are checked they will be set to true.
        checkBoxBool = new boolean[21];
    }

    public int getPlayerID() {
        return playerNum;
    }

    public void setPlayerID(int newPlayerID) {
        playerNum = newPlayerID;
    }

    protected void receiveInfo(GameInfo info) {

    }

    public boolean[] getCheckBoxArray() {
        boolean temp[] = new boolean[21];
        for(int i = 0; i < 21; i++) {
            temp[i] = checkBoxBool[i];
        }
        return temp;
    }

}
