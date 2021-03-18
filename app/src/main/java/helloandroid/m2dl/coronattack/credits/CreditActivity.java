package helloandroid.m2dl.coronattack.credits;

import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.coronattack.R;
import helloandroid.m2dl.coronattack.game.GameConstants;
import helloandroid.m2dl.coronattack.game.mobengine.activities.SoundAppCompatActivity;
import helloandroid.m2dl.coronattack.game.mobengine.resource_stores.SoundStore;

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