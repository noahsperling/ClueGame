package edu.up.cs301.game;

import android.app.Activity;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by sperling19 on 11/9/2016.
 */
public interface CluePlayer{
    /*
    ClueState recentState = null;

    public void gameSetAsGui(GameMainActivity activity);

    // sets this player as the GUI player (overrideable)
    public void setAsGui(GameMainActivity activity);

    // sends a message to the player
    public void sendInfo(GameInfo info);

    // start the player
    public void start();

    // whether this player requires a GUI
    public boolean requiresGui();

    // whether this player supports a GUI
    public boolean supportsGui();

    public ClueState getRecentState();
    //comment + another word*/

    int getPlayerID();
}
