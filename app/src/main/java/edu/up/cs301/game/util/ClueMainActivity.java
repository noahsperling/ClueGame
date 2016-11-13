package edu.up.cs301.game.util;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;

import android.view.View;

/**
 * Created by Eric Imperio on 11/13/2016.
 */

public class ClueMainActivity extends GameMainActivity implements View.OnClickListener {
    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame() {
        return null;
    }
}
