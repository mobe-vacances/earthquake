package helloandroid.m2dl.earthquake.game_over;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    public void back(View view){
        finish();
    }
}