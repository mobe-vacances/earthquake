package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.mobengine.statics.SoundStore;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        SoundStore.playGameOverSoundMediaPlayer();
    }

    public void back(View view){
        SoundStore.stopGameOverSoundMediaPlayer();
        SoundStore.playClickMediaPlayer();
        finish();
    }
}