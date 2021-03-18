package helloandroid.m2dl.coronattack.game.score_and_level_handlers;


import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.mobengine.auto_handlers.AutoHandler;
import helloandroid.m2dl.coronattack.game.state.GameState;

public class AutoScore extends AutoHandler {

    @Override
    protected int getTriggerDelay() {
        return GameConstants.SCORE_AUTO_INCREMENT_TIME;
    }

    @Override
    protected void onTrigger() {
        GameState.increaseScore(GameConstants.SCORE_AUTO_INCREMENT_BASE_VALUE*GameState.getLevel());
    }
}
