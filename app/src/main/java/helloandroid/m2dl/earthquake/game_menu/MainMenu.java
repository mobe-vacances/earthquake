package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.mobengine.utils.PermissionUtil;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;

public class MainMenu extends AppCompatActivity {
    private Intent svc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        this.svc=new Intent(this, BackgroundSoundService.class);
        startService(svc);

        PermissionUtil.checkAndRequestAllPermissions(this);
        VibratorService.requestVibrator(this);
    }

    public void launchGame(View view){
        stopService(this.svc);
        MediaPlayer startSound = MediaPlayer.create(this,R.raw.start);
        startSound.start();
        startActivity(new Intent(MainMenu.this, GameActivity.class));
        VibratorService.heavyClick();
    }

    public void launchCredit(View view) {
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
        startActivity(new Intent(MainMenu.this, Credit.class));
    }

    public void launchRules(View view) {
        MediaPlayer click = MediaPlayer.create(this,R.raw.click);
        click.start();
        startActivity(new Intent(MainMenu.this, Rules.class));
    }
}