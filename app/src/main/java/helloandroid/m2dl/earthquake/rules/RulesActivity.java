package helloandroid.m2dl.earthquake.rules;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }

    public void backMenu(View view){
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
        finish();
    }
}