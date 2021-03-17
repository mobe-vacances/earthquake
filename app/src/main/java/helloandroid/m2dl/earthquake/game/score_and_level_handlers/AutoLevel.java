package helloandroid.m2dl.earthquake.game.score_and_level_handlers;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.auto_handlers.AutoHandler;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class AutoLevel extends AutoHandler {

    @Override
    protected int getTriggerDelay() {
        return GameConstants.LEVEL_AUTO_INCREMENT_TIME;
    }

    @Override
    protected void onTrigger() {
        GameState.incrementLevel();
    }

}
