package helloandroid.m2dl.earthquake.game_menu;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        findViewById(R.id.textViewObligatoire).setVisibility(View.GONE);
        getUsername();
    }

    public void launchGame(View view){
        String userName = ((EditText) findViewById(R.id.usernameEditor)).getText().toString();
        if(userName.replaceAll(" ","").length() == 0){
            findViewById(R.id.textViewObligatoire).setVisibility(View.VISIBLE);
            return ;
        } else {
            findViewById(R.id.textViewObligatoire).setVisibility(View.GONE);
            setUsername(userName);
            Intent intent = new Intent(MainMenu.this, GameActivity.class);
            startActivity(intent);
        }
    }

    public void launchCredit(View view) {
        Intent intent = new Intent(MainMenu.this, Credit.class);
        startActivity(intent);
    }


    public void setUsername(String newUsername) {
        SharedPreferences preferences = getSharedPreferences("score", MODE_PRIVATE);
        String oldUSername = preferences.getString("username", "");
        if(!newUsername.equals(oldUSername)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("id", UUID.randomUUID().toString());
            editor.putString("username", newUsername);
            editor.putInt("highest_score", 0);
            editor.apply();
        }
    }

    public void getUsername() {
        SharedPreferences preferences = getSharedPreferences("score", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        EditText usernameTextEditor = findViewById(R.id.usernameEditor);
        usernameTextEditor.setText(username);
    }


}