package edu.up.cs301.game;

import edu.up.cs301.game.config.GameConfig;

/**
 * Created by Noah on 11/13/2016.
 */

public class ClueMainActivity extends GameMainActivity {

    public GameConfig createDefaultConfig()
    {
        //idk how this works yet
    }

    public LocalGame createLocalGame()
    {
        return new ClueLocalGame();
    }

}
