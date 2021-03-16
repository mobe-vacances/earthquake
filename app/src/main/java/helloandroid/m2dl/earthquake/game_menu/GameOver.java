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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.database.FirebaseDatabaseHandler;
import helloandroid.m2dl.earthquake.game.state.GameState;

import static java.lang.Math.min;

public class GameOver extends AppCompatActivity {

    private int lastHighestScore;

    public Map<String, Integer> worldScore;

    private FirebaseDatabaseHandler fireBaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        worldScore = new HashMap<>();
        fireBaseData = new FirebaseDatabaseHandler(this);
        lastHighestScore = getSharedPreferences("score", Context.MODE_PRIVATE).getInt("highest_score", 0);

        fireBaseData.getAllScores(getUsername(), getUuid(), getHighestScore());
        fireBaseData.addOrUpdateScore(getUsername(), getUuid(), getHighestScore());

        saveLocalHighestScore();
        showScore();
    }

    private int getHighestScore() {
        return GameState.getScore() > lastHighestScore ? GameState.getScore() : lastHighestScore;
    }

    private String getUsername() {
        return getSharedPreferences("score", MODE_PRIVATE).getString("username", "");
    }

    private String getUuid() {
        return getSharedPreferences("score", MODE_PRIVATE).getString("id", "");
    }

    private void saveLocalHighestScore() {
        SharedPreferences preferences = getSharedPreferences("score", Context.MODE_PRIVATE);
        SharedPreferences.Editor localScoreEditor = preferences.edit();
        localScoreEditor.putInt("highest_score", getHighestScore());
        localScoreEditor.apply();
    }

    private void showScore() {
        ((TextView)findViewById(R.id.currentScore)).setText("Votre score : " + GameState.getScore());
        ((TextView)findViewById(R.id.highestScore)).setText("Record : " + getHighestScore());
    }

    public void showScoresTable() {
        ConstraintLayout constraintLayout =findViewById(R.id.scoreConstraint);
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            constraintLayout.getChildAt(i).setVisibility(View.GONE);
        }

        List<Map.Entry<String, Integer>> entries = new LinkedList(worldScore.entrySet());
        entries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        for (int i = 0; i < min(worldScore.size(), constraintLayout.getChildCount()); i++) {
            LinearLayout linearLayout = (LinearLayout) constraintLayout.getChildAt(i);
            linearLayout.setVisibility(View.VISIBLE);
            ((TextView) linearLayout.getChildAt(1)).setText(entries.get(i).getKey().split("@")[1]);
            ((TextView) linearLayout.getChildAt(2)).setText(" || " + entries.get(i).getValue().toString());
        }
    }


    public void back(View view) {
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}