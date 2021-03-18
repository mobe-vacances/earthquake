package helloandroid.m2dl.earthquake.game_menu;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.database.FirebaseInstallationService;
import helloandroid.m2dl.earthquake.game.database.HighscoreHandler;
import helloandroid.m2dl.earthquake.game.database.WorldScoresHandler;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);

        EditText editText = findViewById(R.id.usernameEditor);
        SharedPreferences preferences = getSharedPreferences(GameConstants.APP_SHARED_PREFERENCES_KEY, MODE_PRIVATE);

        editText.setText(
                    preferences.getString(
                        "username",
                        getResources().getString(R.string.default_player_name)
                )
        );

        editText.addTextChangedListener(new UsernameChangedListener(preferences));

        FirebaseInstallationService.init();
        WorldScoresHandler.init();
    }

    public void lauchGame(View view) {
        startActivity(new Intent(MainMenu.this, GameActivity.class));
    }


}