package helloandroid.m2dl.earthquake.game.enemy;

import android.graphics.Rect;
import android.os.Handler;

import helloandroid.m2dl.earthquake.game.mobengine.RandomService;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.score_and_level_handlers.AutoHandler;

public class EnemySpawn implements AutoHandler {

    private static final int LEFT = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;

    private Rect playerHitbox;

    private final Handler handler = new Handler();

    private final Runnable addEnemy = () -> {
        if(GameEngine.isRunning()) {
            Enemy enemy = new Enemy(playerHitbox, newEnemyRectOutsideOfGameRect());
            GameEngine.addGameElements(enemy);
        }
        handler.postDelayed(this.addEnemy, (long) ((GameConstants.ENEMY_BASE_SPAWN_RATE + RandomService.get().nextInt(GameConstants.ENEMY_SPAWN_RATE_VARIATION))  / (GameState.getLevel() * BulletTime.getBulletTimeMultiplier())));
    };

    public EnemySpawn(Rect playerHitbox) {
        this.playerHitbox = playerHitbox;
        handler.postDelayed(this.addEnemy, (long) ((GameConstants.BONUS_MIN_DELAY + RandomService.get().nextInt(GameConstants.BONUS_MAX_DELAY_VARIATION)) / (GameState.getLevel() * BulletTime.getBulletTimeMultiplier())));
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
    public void removeCallback() {
        handler.removeCallbacksAndMessages(null);
    }
}
