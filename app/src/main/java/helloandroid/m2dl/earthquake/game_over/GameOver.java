package helloandroid.m2dl.earthquake.game_over;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.main_menu.MainMenu;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    public void back(View view){
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
    }
}