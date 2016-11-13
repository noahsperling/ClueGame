package edu.up.cs301.game;

import edu.up.cs301.game.config.GameConfig;

/**
 * Created by Noah on 11/13/2016.
 */

public class ClueMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 6732;

    public GameConfig createDefaultConfig()
    {
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
        //idk how this works yet
        return new GameConfig(3, 6, "Clue", PORT_NUMBER);
    }

    public LocalGame createLocalGame()
    {
        return new ClueLocalGame();
    }

}
