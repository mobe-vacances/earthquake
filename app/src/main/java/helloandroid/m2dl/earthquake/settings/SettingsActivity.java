package helloandroid.m2dl.earthquake.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.activities.SoundAppCompatActivity;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class SettingsActivity extends SoundAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVibrationIconVisibiity();
        setMusiqueIconVisibiity();
        setAnimationIconVisibiity();
    }


    public void backMenu(View view) {
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);
        finish();
    }

    public void clickSettingVibration(View view){
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);

        VibratorService.setVibrationsActive(!VibratorService.isVibrationsActive());

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("vibrationActive", VibratorService.isVibrationsActive());
        localScoreEditor.apply();

        setVibrationIconVisibiity();
    }

    private void setVibrationIconVisibiity(){
        findViewById(R.id.idNoVibration).setVisibility(VibratorService.isVibrationsActive() ? View.GONE : View.VISIBLE);
    }

    public void clickSettingMusique(View view){
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);

        SoundStore.setMute(!SoundStore.isMute());

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("mute", SoundStore.isMute());
        localScoreEditor.apply();

        setMusiqueIconVisibiity();

        SoundStore.loopSound(R.raw.menu, GameConstants.MENU_MUSIC_VOLUME);
    }

    private void setMusiqueIconVisibiity(){
        findViewById(R.id.idNoMusique).setVisibility(SoundStore.isMute() ? View.VISIBLE : View.GONE);
    }

    public void clickSettingAnimation(View view){
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);

        GameState.setAnimationsActive(!GameState.getAnimationsActive());

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("animationsActive", GameState.getAnimationsActive());
        localScoreEditor.apply();

        setAnimationIconVisibiity();
    }

    private void setAnimationIconVisibiity(){
        findViewById(R.id.idNoAnimation).setVisibility(GameState.getAnimationsActive() ? View.GONE : View.VISIBLE);
    }
}