package helloandroid.m2dl.earthquake.game.score_and_level_handlers;


import android.os.Handler;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class AutoScore implements AutoHandler {

    private Handler handler = new Handler();

    private Runnable addScore = () -> {
        if(GameEngine.isRunning()) {
            GameState.increaseScore(GameConstants.SCORE_AUTO_INCREMENT_BASE_VALUE*GameState.getLevel());
        }
        handler.postDelayed(this.addScore, GameConstants.SCORE_AUTO_INCREMENT_TIME);
    };

    public AutoScore() {
        handler.postDelayed(this.addScore, GameConstants.SCORE_AUTO_INCREMENT_TIME);
    }

    @Override
    public void removeCallback() {
        handler.removeCallbacksAndMessages(null);
    }
}
