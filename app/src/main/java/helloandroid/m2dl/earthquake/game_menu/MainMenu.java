package helloandroid.m2dl.earthquake.game_menu;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.database.FirebaseInstallationService;
import helloandroid.m2dl.earthquake.game.database.HighscoreHandler;
import helloandroid.m2dl.earthquake.game.database.WorldScoresHandler;
import helloandroid.m2dl.earthquake.game.mobengine.utils.PermissionUtil;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;

public class MainMenu extends AppCompatActivity {
    private Intent svc;


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

        this.svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);

        PermissionUtil.checkAndRequestAllPermissions(this);
        VibratorService.requestVibrator(this);
    }

    public void launchGame(View view){
        stopService(this.svc);
        MediaPlayer startSound = MediaPlayer.create(this,R.raw.start);
        startSound.start();
        startActivity(new Intent(MainMenu.this, GameActivity.class));
        VibratorService.heavyClick();
    }

    public void launchCredit(View view) {
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
        startActivity(new Intent(MainMenu.this, Credit.class));
    }

    public void launchRules(View view) {
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
        startActivity(new Intent(MainMenu.this, Rules.class));
    }

    public void lauchGame(View view) {
        startActivity(new Intent(MainMenu.this, GameActivity.class));
    }


}