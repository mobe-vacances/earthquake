package helloandroid.m2dl.earthquake.game.enemy;

import android.graphics.Rect;

import helloandroid.m2dl.earthquake.game.mobengine.auto_handlers.AutoHandler;
import helloandroid.m2dl.earthquake.game.mobengine.utils.RandomService;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.player.Player;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.core.GameEngine;

public class EnemySpawn extends AutoHandler {

    private static final int LEFT = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;

    private Player player;

    public EnemySpawn(Player player) {
        this.player = player;
    }

    private static Rect newEnemyRectOutsideOfGameRect() {
        int x, y;
        switch(RandomService.get().nextInt(4)) {
            case LEFT:
                y = GameConstants.HEADER_HEIGHT + RandomService.get().nextInt(GameState.getGameRect().height());
                return new Rect(
                        GameState.getGameRect().left - 1,
                        y,
                        GameState.getGameRect().left,
                        y + 1
                );
            case TOP:
                x = RandomService.get().nextInt(GameState.getGameRect().width());
                return new Rect(
                        x,
                        GameState.getGameRect().top - 1,
                        x + 1,
                        GameState.getGameRect().top
                );
            case RIGHT:
                y = GameConstants.HEADER_HEIGHT + RandomService.get().nextInt(GameState.getGameRect().height());
                return new Rect(
                        GameState.getGameRect().right ,
                        y,
                        GameState.getGameRect().right + 1,
                        y + 1
                );
            case BOTTOM:
                x = RandomService.get().nextInt(GameState.getGameRect().width());
                return new Rect(
                        x,
                        GameState.getGameRect().bottom ,
                        x + 1,
                        GameState.getGameRect().bottom + 1
                );
        }
        return null;
    }

    @Override
    protected int getTriggerDelay() {
        return (int) ( RandomService.nextIntBetween(GameConstants.ENEMY_BASE_SPAWN_RATE_MIN, GameConstants.ENEMY_BASE_SPAWN_RATE_MAX) / GameState.getLevel() * BulletTime.getBulletTimeMultiplier());
    }

    @Override
    protected void onTrigger() {
        Enemy enemy = new Enemy(player, newEnemyRectOutsideOfGameRect());
        GameEngine.addGameElements(enemy);
    }
}
