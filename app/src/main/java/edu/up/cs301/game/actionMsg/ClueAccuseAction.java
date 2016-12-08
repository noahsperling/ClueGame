package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
/*Sent by player to the ClueLocalGame to make an Accusation about:
 *  who committed the murder (suspect)
 *  Where the murder was committed (room)
 *  And With what the murder was committed (weapon)
 *Extends ClueMoveAction to be passed into makeMove() in the ClueLocalGame
 *Implements Serializable for network play
 */
public class ClueAccuseAction extends ClueMoveAction implements Serializable
{
    //Long for network play
    private static final long serialVersionUID = 3067345643652014L;

    //Note: These are made strings because the Spinners store strings
    public String room; //Name of Room the player is stating the murder was committed in
    public String suspect; //Name of Suspect player is stating committed the murder
    public String weapon; //Name of Weapon the player is stating the murder was committed with

    public ClueAccuseAction(GamePlayer player)
    {
        super(player);
    }
}
