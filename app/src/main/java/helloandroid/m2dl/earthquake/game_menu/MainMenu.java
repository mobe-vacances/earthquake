package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        getUsername();
    }

    public void lauchGame(View view) {
        EditText usernameTextEditor = findViewById(R.id.usernameEditor);
        setUsername(usernameTextEditor.getText().toString());
        Intent intent = new Intent(MainMenu.this, GameActivity.class);
        startActivity(intent);
    }

    public void setUsername(String newUsername) {
        SharedPreferences preferences = getSharedPreferences("score", MODE_PRIVATE);
        String oldUSername = preferences.getString("username", newUsername);
        System.out.println("different ?" + (!newUsername.equals(oldUSername)));
        if(!newUsername.equals(oldUSername)) {
            SharedPreferences.Editor editor = preferences.edit();
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