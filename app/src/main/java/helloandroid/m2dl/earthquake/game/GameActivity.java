package helloandroid.m2dl.earthquake.game;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.bonus.BonusSpawn;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.auto_handlers.AutoHandlerStore;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.sensors.SensorManagerService;
import helloandroid.m2dl.earthquake.game.onTouch.OnTouchListener;
import helloandroid.m2dl.earthquake.game.enemy.EnemySpawn;
import helloandroid.m2dl.earthquake.game.background.Background;
import helloandroid.m2dl.earthquake.game.header.Header;
import helloandroid.m2dl.earthquake.game.header.LevelDisplay;
import helloandroid.m2dl.earthquake.game.pause.Pause;
import helloandroid.m2dl.earthquake.game.pause.PauseDisplay;
import helloandroid.m2dl.earthquake.game.player.Player;
import helloandroid.m2dl.earthquake.game.header.ScoreDisplay;
import helloandroid.m2dl.earthquake.game.player.AccelerometerEventListener;
import helloandroid.m2dl.earthquake.game.background.LightEventListener;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.MobeGameActivity;
import helloandroid.m2dl.earthquake.game.score_and_level_handlers.AutoLevel;
import helloandroid.m2dl.earthquake.game.score_and_level_handlers.AutoScore;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game_menu.GameOver;

public class GameActivity extends MobeGameActivity {

    private static final int[] USED_BITMAP_IDS = {
            R.drawable.world,
            R.drawable.coronavirus_safe,
            R.drawable.coronavirus,
            R.drawable.mask
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameEngine.reset();

        BitmapStore.decodeBitmaps(USED_BITMAP_IDS, getResources());

        GameState.resetGameState(() -> {
            startActivity(new Intent(GameActivity.this, GameOver.class));
            GameEngine.reset();
            finish();
        });

        getGameView().setOnTouchListener(new OnTouchListener());

        Player player = new Player(this::runOnUiThread);
        Background background = new Background();

        SensorManagerService.requestSensorManager(this);
        SensorManagerService.addSensorListeners(
                new AccelerometerEventListener(player),
                new LightEventListener(background)
        );

        AutoHandlerStore.addAutoHandlers(
                new BonusSpawn(player.getHitbox()),
                new EnemySpawn(player),
                new AutoScore(),
                new AutoLevel()
        );


        GameEngine.addGameElements(
                new Header(),
                new ScoreDisplay(),
                new LevelDisplay(),
                new Pause(),
                new PauseDisplay(),
                new BulletTime(),
                background,
                player
        );
    }

}
