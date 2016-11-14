package edu.up.cs301.game;

import java.util.ArrayList;

import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * Created by Noah on 11/13/2016.
 */

public class ClueMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 6732;

    @Override
    public GameConfig createDefaultConfig()
    {
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("Human Player") {
            public GamePlayer createPlayer(String name) {
                return new ClueHumanPlayer(name, R.layout.game_no_gui);
            }
        });
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new ComputerPlayerDumb(name);
            }
        });
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new ComputerPlayerSmart(name);
            }
        });

        GameConfig defaultConfig = new GameConfig(playerTypes, 3, 6, "Clue", PORT_NUMBER);

        //default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Dumb Computer", 1);
        defaultConfig.addPlayer("dumb Computer 2", 1);

        return defaultConfig;
    }

    public LocalGame createLocalGame()
    {
        return new ClueLocalGame();
    }

}
