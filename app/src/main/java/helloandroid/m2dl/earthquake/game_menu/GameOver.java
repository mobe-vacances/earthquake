package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.database.FirebaseDatabaseHandler;
import helloandroid.m2dl.earthquake.game.state.GameState;

import static java.lang.Math.min;

public class GameOver extends AppCompatActivity {

    private int highestScore;

    private int currentScore;

    public Map<String, Integer> worldScore;

    private FirebaseDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        db = new FirebaseDatabaseHandler(this);
        worldScore = new HashMap<>();
        loadHighestScore();
        loadCurrentScore();
        updateHighestScore();
        showScore();
        updateTable();
    }

    private void loadHighestScore() {
        SharedPreferences preferences = getSharedPreferences("score", Context.MODE_PRIVATE);
        highestScore = preferences.getInt("highest_score", 0);
    }

    private void loadCurrentScore() {
        currentScore = GameState.getScore();
    }

    private void updateHighestScore() {
        if (currentScore > highestScore) {
            highestScore = currentScore;
            db.addOrUpdateScore(getUsername(), highestScore);
        }
    }

    public String getUsername() {
        SharedPreferences preferences = getSharedPreferences("score", MODE_PRIVATE);
        return preferences.getString("username", "");
    }

    private void saveHighestScore() {
        SharedPreferences preferences = getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putInt("highest_score", highestScore);
        localScoreEditor.apply();
    }

    private void showScore() {
        TextView currentScoreView = findViewById(R.id.currentScore);
        currentScoreView.setText(currentScoreView.getText().toString() + currentScore);
        TextView highestScoreView = findViewById(R.id.highestScore);
        highestScoreView.setText(highestScoreView.getText().toString() + highestScore);
    }

    public void updateTable() {
        db.getAllScores();
    }

    public void fillScoresTable() {
        LinearLayout scoresTable = findViewById(R.id.scoresLayout);
        ConstraintLayout scoresLayout = (ConstraintLayout) scoresTable.getChildAt(0);

        List<String> keys = new ArrayList<>(worldScore.keySet());
        List<Map.Entry<String, Integer>> entries = new LinkedList(worldScore.entrySet());
        entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        for (int i = 0; i < min(scoresLayout.getChildCount(), worldScore.size()); i++) {
            LinearLayout line = (LinearLayout) scoresLayout.getChildAt(i);
            ConstraintLayout cr = (ConstraintLayout) line.getChildAt(0);
            TextView name = (TextView) cr.getChildAt(1);
            TextView score = (TextView) cr.getChildAt(2);
            Map.Entry e = entries.get(i);
            name.setText(e.getKey().toString());
            score.setText(e.getValue().toString());
        }
    }

    public void back(View view) {
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        saveHighestScore();
        super.onDestroy();
    }
}