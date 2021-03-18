package helloandroid.m2dl.earthquake.game;

import android.content.Intent;
import android.hardware.Sensor;
import android.media.MediaPlayer;
import android.os.Bundle;

import helloandroid.m2dl.earthquake.game.bonus.BonusSpawn;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.auto_handlers.AutoHandlerStore;
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
import helloandroid.m2dl.earthquake.game_over.GameOverActivity;

public class GameActivity extends MobeGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameEngine.reset();

        GameState.resetGameState(() -> {
            startActivity(new Intent(GameActivity.this, GameOverActivity.class));
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
