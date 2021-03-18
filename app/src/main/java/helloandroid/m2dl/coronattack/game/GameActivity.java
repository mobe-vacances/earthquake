package helloandroid.m2dl.coronattack.game;

import android.content.Intent;
import android.os.Bundle;

import helloandroid.m2dl.coronattack.R;
import helloandroid.m2dl.coronattack.game.bonus.BonusSpawn;
import helloandroid.m2dl.coronattack.game.bullet_time.BulletTime;
import helloandroid.m2dl.coronattack.game.mobengine.auto_handlers.AutoHandlerStore;
import helloandroid.m2dl.coronattack.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.coronattack.game.mobengine.sensors.SensorManagerService;
import helloandroid.m2dl.coronattack.game.onTouch.OnTouchListener;
import helloandroid.m2dl.coronattack.game.enemy.EnemySpawn;
import helloandroid.m2dl.coronattack.game.background.Background;
import helloandroid.m2dl.coronattack.game.header.Header;
import helloandroid.m2dl.coronattack.game.header.LevelDisplay;
import helloandroid.m2dl.coronattack.game.pause.Pause;
import helloandroid.m2dl.coronattack.game.pause.PauseDisplay;
import helloandroid.m2dl.coronattack.game.player.Player;
import helloandroid.m2dl.coronattack.game.header.ScoreDisplay;
import helloandroid.m2dl.coronattack.game.player.AccelerometerEventListener;
import helloandroid.m2dl.coronattack.game.background.LightEventListener;
import helloandroid.m2dl.coronattack.game.mobengine.core.GameEngine;
import helloandroid.m2dl.coronattack.game.mobengine.activities.MobeGameActivity;
import helloandroid.m2dl.coronattack.game.score_and_level_handlers.AutoLevel;
import helloandroid.m2dl.coronattack.game.score_and_level_handlers.AutoScore;
import helloandroid.m2dl.coronattack.game.state.GameState;
import helloandroid.m2dl.coronattack.game_over.GameOverActivity;

public class GameActivity extends MobeGameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameEngine.reset();

        GameState.resetGameState(() -> {
            startActivity(new Intent(GameActivity.this, GameOverActivity.class));
            GameEngine.reset();
            finish();
        }, getSharedPreferences("settings", MODE_PRIVATE).getBoolean("animationsActive", true));

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

        SoundStore.loopSound(R.raw.game, GameConstants.GAME_MUSIC_VOLUME);
    }

}
