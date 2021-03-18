package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;

public class Credit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
    }

    public void backMenu(View view) {
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
        finish();
    }
}