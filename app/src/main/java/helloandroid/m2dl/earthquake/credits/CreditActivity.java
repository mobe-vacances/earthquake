package helloandroid.m2dl.earthquake.credits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.activities.SoundActivity;
import helloandroid.m2dl.earthquake.game.mobengine.activities.SoundAppCompatActivity;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;

public class CreditActivity extends SoundAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
    }

    public void backMenu(View view) {
        SoundStore.playSound(R.raw.click, GameConstants.CLICK_SOUND_VOLUME);
        finish();
    }
}