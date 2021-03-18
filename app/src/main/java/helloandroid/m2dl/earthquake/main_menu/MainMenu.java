package helloandroid.m2dl.earthquake.main_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.credits.Credit;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.utils.PermissionUtil;
import helloandroid.m2dl.earthquake.game.mobengine.utils.VibratorService;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        PermissionUtil.checkAndRequestAllPermissions(this);
        VibratorService.requestVibrator(this);
        BitmapStore.decodeBitmaps(GameConstants.USED_BITMAP_IDS, getResources());
    }

    public void launchGame(View view){
        startActivity(new Intent(MainMenu.this, GameActivity.class));
        VibratorService.heavyClick();
    }

    public void launchCredit(View view) {
        startActivity(new Intent(MainMenu.this, Credit.class));
    }

    public void launchRules(View view) {
        startActivity(new Intent(MainMenu.this, Rules.class));
    }
}