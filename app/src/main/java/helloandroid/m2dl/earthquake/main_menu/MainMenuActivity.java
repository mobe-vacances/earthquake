package helloandroid.m2dl.earthquake.main_menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.credits.CreditActivity;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.mobengine.activities.SoundAppCompatActivity;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.database.FirebaseInstallationService;
import helloandroid.m2dl.earthquake.game.database.WorldScoresHandler;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.utils.PermissionUtil;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game_menu.Settings;
import helloandroid.m2dl.earthquake.game_menu.UsernameChangedListener;
import helloandroid.m2dl.earthquake.rules.RulesActivity;

public class MainMenuActivity extends SoundAppCompatActivity {

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



        PermissionUtil.checkAndRequestAllPermissions(this);
        VibratorService.requestVibrator(this);
        BitmapStore.decodeBitmaps(GameConstants.USED_BITMAP_IDS, getResources());
        SoundStore.createMediaPlayers(GameConstants.USED_SOUNDS_IDS, this);

        SoundStore.loopSound(R.raw.menu_encore_rip_droit, GameConstants.MENU_MUSIC_VOLUME);

        initSettings();
    }

    public void launchGame(View view){
        SoundStore.stopLoopedSound(R.raw.menu_encore_rip_droit);

        SoundStore.playSound(R.raw.start, GameConstants.START_SOUND_VOLUME);

        startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
        VibratorService.heavyClick();
    }

    public void launchCredit(View view) {
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);
        startActivity(new Intent(MainMenuActivity.this, CreditActivity.class));
    }

    public void launchRules(View view) {
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);
        startActivity(new Intent(MainMenuActivity.this, RulesActivity.class));
    }



    public void launchSettings(View view) {
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);
        startActivity(new Intent(MainMenuActivity.this, Settings.class));
    }

    private void initSettings(){
        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        VibratorService.setVibrationsActive(preferences.getBoolean("vibrationActive", true));
        SoundStore.setMute(preferences.getBoolean("mute", false));
        GameState.setAnimationsActive(preferences.getBoolean("animationsActive", true));
    }
}