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
        VibratorService.setAnimationActive(!VibratorService.getAnimationActive());

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("vibrationActive", VibratorService.getAnimationActive());
        localScoreEditor.apply();

        setVibrationIconVisibiity();
    }

    private void setVibrationIconVisibiity(){
        findViewById(R.id.idNoVibration).setVisibility(VibratorService.getAnimationActive() ? View.GONE : View.VISIBLE);
    }

    public void clickSettingMusique(View view){
        SoundStore.setAnimationActive(!SoundStore.getAnimationActive());

        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("soundActive", SoundStore.getAnimationActive());
        localScoreEditor.apply();

        setMusiqueIconVisibiity();
    }

    private void setMusiqueIconVisibiity(){
        findViewById(R.id.idNoMusique).setVisibility(SoundStore.getAnimationActive() ? View.GONE : View.VISIBLE);
    }

    public void clickSettingAnimation(View view){
        Bonus.setAnimationActive(!Bonus.getAnimationActive());
        Enemy.setAnimationActive(Bonus.getAnimationActive());
        Player.setAnimationActive(Bonus.getAnimationActive());



        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putBoolean("playerActive", SoundStore.getAnimationActive());
        localScoreEditor.putBoolean("enemyActive", SoundStore.getAnimationActive());
        localScoreEditor.putBoolean("bonusActive", SoundStore.getAnimationActive());
        localScoreEditor.apply();

        setAnimationIconVisibiity();
    }

    private void setAnimationIconVisibiity(){
        findViewById(R.id.idNoAnimation).setVisibility(Bonus.getAnimationActive() ? View.GONE : View.VISIBLE);
    }
}