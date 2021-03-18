package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.bonus.Bonus;
import helloandroid.m2dl.earthquake.game.enemy.Enemy;
import helloandroid.m2dl.earthquake.game.mobengine.statics.SoundStore;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.earthquake.game.player.Player;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setVibrationIconVisibiity();
        setMusiqueIconVisibiity();
        setAnimationIconVisibiity();
    }


    public void backMenu(View view) {
        Intent intent = new Intent(Settings.this, MainMenu.class);
        startActivity(intent);
    }

    public void clickSettingVibration(View view){
        SoundStore.playClickMediaPlayer();

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
        SoundStore.playClickMediaPlayer();

        SoundStore.setMute(!SoundStore.isMute());

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("mute", SoundStore.isMute());
        localScoreEditor.apply();

        setMusiqueIconVisibiity();

        if(!SoundStore.isMute()){
            startService(SoundStore.getMainMenuBackgroundSoundIntent());
        }else{
            stopService(SoundStore.getMainMenuBackgroundSoundIntent());
        }
    }

    private void setMusiqueIconVisibiity(){
        findViewById(R.id.idNoMusique).setVisibility(SoundStore.isMute() ? View.VISIBLE : View.GONE);
    }

    public void clickSettingAnimation(View view){
        SoundStore.playClickMediaPlayer();

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