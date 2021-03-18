package helloandroid.m2dl.earthquake.game_menu;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.bonus.Bonus;
import helloandroid.m2dl.earthquake.game.enemy.Enemy;
import helloandroid.m2dl.earthquake.game.mobengine.statics.SoundStore;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.database.FirebaseInstallationService;
import helloandroid.m2dl.earthquake.game.database.HighscoreHandler;
import helloandroid.m2dl.earthquake.game.database.WorldScoresHandler;
import helloandroid.m2dl.earthquake.game.mobengine.utils.PermissionUtil;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.earthquake.game.player.Player;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        EditText editText = findViewById(R.id.usernameEditor);
        SharedPreferences preferences = getSharedPreferences(GameConstants.APP_SHARED_PREFERENCES_KEY, MODE_PRIVATE);

        editText.setText(
                    preferences.getString(
                        "username",
                        getResources().getString(R.string.default_player_name)
                )
        );

        editText.addTextChangedListener(new UsernameChangedListener(preferences));

        FirebaseInstallationService.init();
        WorldScoresHandler.init();

        SoundStore.createMainMenuBackgroundSoundIntent(this);
        startService(SoundStore.getMainMenuBackgroundSoundIntent());

        SoundStore.createClickMediaPlayer(this);
        SoundStore.createStartSoundMediaPlayer(this);
        SoundStore.createGameOverSoundMediaPlayer(this);

        PermissionUtil.checkAndRequestAllPermissions(this);
        VibratorService.requestVibrator(this);

        initSettings();
    }

    public void launchGame(View view){
        stopService(SoundStore.getMainMenuBackgroundSoundIntent());

        SoundStore.playStartSoundMediaPlayer();

        startActivity(new Intent(MainMenu.this, GameActivity.class));
        VibratorService.heavyClick();
    }

    public void launchCredit(View view) {
        SoundStore.playClickMediaPlayer();
        startActivity(new Intent(MainMenu.this, Credit.class));
    }

    public void launchRules(View view) {
        SoundStore.playClickMediaPlayer();
        startActivity(new Intent(MainMenu.this, Rules.class));
    }



    public void launchSettings(View view) {
        SoundStore.playClickMediaPlayer();
        startActivity(new Intent(MainMenu.this, Settings.class));
    }

    private void initSettings(){
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        VibratorService.setVibrationsActive(preferences.getBoolean("vibrationActive", true));
        SoundStore.setMute(preferences.getBoolean("mute", false));
        GameState.setAnimationsActive(preferences.getBoolean("animationsActive", true));
    }
}