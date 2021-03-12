package helloandroid.m2dl.earthquake.game.score_and_level_handlers;

import android.os.Handler;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class AutoLevel implements AutoHandler {

    private final Handler handler = new Handler();

    private final Runnable addLevel = () -> {
        GameState.incrementLevel();
        handler.postDelayed(this.addLevel, GameConstants.LEVEL_AUTO_INCREMENT_TIME);
    };

    public AutoLevel() {
        handler.postDelayed(this.addLevel, GameConstants.LEVEL_AUTO_INCREMENT_TIME);
    }

    @Override
    public void removeCallback() {
        handler.removeCallbacksAndMessages(null);
    }
}
