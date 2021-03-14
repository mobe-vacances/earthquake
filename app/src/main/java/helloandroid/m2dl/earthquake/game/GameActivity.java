package helloandroid.m2dl.earthquake.game;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.bonus.BonusSpawn;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
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
import helloandroid.m2dl.earthquake.game.score_and_level_handlers.AutoHandler;
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
    protected int[] getUsedSoundIds() {
        return new int[0];
    }

    @Override
    protected int[] getUsedBitmapsIds() {
        return USED_BITMAP_IDS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<AutoHandler> autoHandlers = new ArrayList<>();

        GameState.resetGameState(() -> {
            startActivity(new Intent(GameActivity.this, GameOver.class));
            for(AutoHandler autoHandler : autoHandlers) {
                autoHandler.removeCallback();
            }
            GameEngine.reset();
            finish();
        });

        getGameView().setOnTouchListener(new OnTouchListener());

        Player player = new Player();
        Background background = new Background();

        getBaseSensorEventListeners().add(new AccelerometerEventListener(
                getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                player
        ));

        getBaseSensorEventListeners().add(new LightEventListener(
                getSensorManager().getDefaultSensor(Sensor.TYPE_LIGHT),
                background
        ));

        autoHandlers.add(new BonusSpawn(player.getHitbox()));
        autoHandlers.add(new EnemySpawn(player.getHitbox()));
        autoHandlers.add(new AutoScore());
        autoHandlers.add(new AutoLevel());

        GameEngine.reset();
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
        GameEngine.start();
    }
}
