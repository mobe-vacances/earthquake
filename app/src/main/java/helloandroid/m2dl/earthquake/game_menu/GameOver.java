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

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class GameOver extends AppCompatActivity {

    int highestScore;

    int currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        loadHighestScore();
        loadCurrentScore();
        updateHighestScore();
        showScore();
        fillScoresTable();
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
        }
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

    private void fillScoresTable() {
        LinearLayout scoresTable = findViewById(R.id.scoresLayout);
        ConstraintLayout scoresLayout = (ConstraintLayout) scoresTable.getChildAt(0);

        for (int i = 0; i < scoresLayout.getChildCount(); i++) {
            LinearLayout line = (LinearLayout) scoresLayout.getChildAt(i);
            ConstraintLayout cr = (ConstraintLayout) line.getChildAt(0);
            TextView name = (TextView) cr.getChildAt(1);
            TextView score = (TextView) cr.getChildAt(2);

            name.setText("Name " + i);
            score.setText("Score " + i);
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