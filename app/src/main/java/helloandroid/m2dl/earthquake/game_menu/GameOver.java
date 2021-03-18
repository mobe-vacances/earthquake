package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;

public class GameOver extends AppCompatActivity {
    MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        music = MediaPlayer.create(this,R.raw.libre_de_droit);
        music.start();
    }

    public void back(View view){
        music.stop();
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();

        finish();
    }
}