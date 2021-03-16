package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
    }

    public void lauchGame(View view){
        Intent intent = new Intent(MainMenu.this, GameActivity.class);
        startActivity(intent);
    }

    public void lauchCredit(View view) {
        Intent intent = new Intent(MainMenu.this, Credit.class);
        startActivity(intent);
    }
}