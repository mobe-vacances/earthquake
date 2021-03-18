package helloandroid.m2dl.earthquake.main_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.credits.CreditActivity;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.earthquake.game.mobengine.utils.PermissionUtil;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;
import helloandroid.m2dl.earthquake.game_menu.BackgroundSoundService;
import helloandroid.m2dl.earthquake.rules.RulesActivity;

public class MainMenuActivity extends AppCompatActivity {
    private Intent svc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        this.svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);

        PermissionUtil.checkAndRequestAllPermissions(this);
        VibratorService.requestVibrator(this);
        BitmapStore.decodeBitmaps(GameConstants.USED_BITMAP_IDS, getResources());
        SoundStore.createMediaPlayers(GameConstants.USED_SOUNDS_IDS, this);
    }

    public void launchGame(View view){
        startActivity(new Intent(MainMenuActivity.this, GameActivity.class));
        stopService(this.svc);
        MediaPlayer startSound = MediaPlayer.create(this,R.raw.start);
        startSound.start();
        VibratorService.heavyClick();
    }

    public void launchCredit(View view) {
        startActivity(new Intent(MainMenuActivity.this, CreditActivity.class));
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
    }

    public void launchRules(View view) {
        startActivity(new Intent(MainMenuActivity.this, RulesActivity.class));
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
    }
}