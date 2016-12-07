package edu.up.cs301.game.actionMsg;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;

/**
 * Created by Paige on 11/8/16.
 */
public class ClueSuggestionAction extends ClueMoveAction implements Serializable
{
    private static final long serialVersionUID = 306720547876925L;

    public ClueSuggestionAction(GamePlayer player)
    {
        super(player);
    }
    public String room;
    public String suspect;
    public String weapon;
}
