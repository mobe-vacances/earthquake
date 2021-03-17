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

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        BitmapStore.decodeBitmaps(GameConstants.USED_BITMAP_IDS, getResources());
    }

    public void launchGame(View view){
        Intent intent = new Intent(MainMenu.this, GameActivity.class);
        startActivity(intent);
    }

    public void launchCredit(View view) {
        Intent intent = new Intent(MainMenu.this, Credit.class);
        startActivity(intent);
    }
}