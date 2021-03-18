package helloandroid.m2dl.earthquake.rules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }

    public void backMenu(View view){
        SoundStore.playClickMediaPlayer();
        finish();
    }
}