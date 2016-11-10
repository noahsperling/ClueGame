package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by sperling19 on 11/9/2016.
 */
public class CluePlayer implements GamePlayer{

    private ClueState recentState;

    public void gameSetAsGui(GameMainActivity activity)
    {

    }

    // sets this player as the GUI player (overrideable)
    public void setAsGui(GameMainActivity activity)
    {

    }

    // sends a message to the player
    public void sendInfo(GameInfo info)
    {

    }

    // start the player
    public void start()
    {

    }

    // whether this player requires a GUI
    public boolean requiresGui()
    {
        return true;
    }

    // whether this player supports a GUI
    public boolean supportsGui()
    {
        return true;
    }

    public ClueState getRecentState()
    {
        return recentState;
    }
    //comment + another word
}
