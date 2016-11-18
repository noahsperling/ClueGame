package edu.up.cs301.game;

import java.util.Random;

import edu.up.cs301.game.actionMsg.ClueMoveDownAction;
import edu.up.cs301.game.actionMsg.ClueMoveLeftAction;
import edu.up.cs301.game.actionMsg.ClueMoveRightAction;
import edu.up.cs301.game.actionMsg.ClueMoveUpAction;
import edu.up.cs301.game.actionMsg.ClueRollAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by Langley on 11/17/2016.
 */

public class ComputerPlayerDumb extends ClueComputerPlayer {

    public ComputerPlayerDumb(String name){
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        ClueState myState = (ClueState)info; //cast it

        //return because it is not the AI's turn
        if (myState.getTurnId() != this.playerID) {
            return;
        }

        // delay for a second to make opponent think we're thinking
        sleep(1000);

        //get its hand in case we need to do something with this
        myState.getCards(this.playerID);


        //roll the die
        //check to see if the player can roll

        //These actions take in a GamePlayer
        if (myState.getCanRoll(this.playerID) == true) {
//            game.sendAction(new ClueRollAction(ClueComputerPlayer) );
        }
        Random rand = new Random();
        int move = rand.nextInt(4);
        if (move == 1) {
//            game.sendAction(new ClueMoveLeftAction((ClueComputerPlayer));
        }
        if (move == 2) {
//            game.sendAction(new ClueMoveUpAction((ClueComputerPlayer));
        }
        if (move == 3) {
//            game.sendAction(new ClueMoveRightAction((ClueComputerPlayer));
        }
        if (move == 4) {
//            game.sendAction(new ClueMoveDownAction((ClueComputerPlayer));
        }




        //if it enters a room, suggest random
        //if it uses all its moves and does not enter a room, end turn
    }

}
