package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.database.HighscoreHandler;
import helloandroid.m2dl.earthquake.game.database.WorldScoresHandler;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.scores.Score;

import static java.lang.Math.min;

public class GameOver extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Score score = new Score(getUsername(), GameState.getScore());
        HighscoreHandler.updateHighScore(score);

        showScore();
        showScoresTable();
    }

    private int getHighestScore() {
        if(HighscoreHandler.getHighScore() != null) {
            return Math.max(GameState.getScore(), HighscoreHandler.getHighScore().getValue());
        }

        return GameState.getScore();
    }

    private String getUsername() {
        return getPreferences( MODE_PRIVATE).getString("username", getResources().getString(R.string.default_player_name));
    }

    private void showScore() {
        ((TextView)findViewById(R.id.currentScore)).setText("Votre score : " + GameState.getScore());
        ((TextView)findViewById(R.id.highestScore)).setText("Record : " + getHighestScore());
    }

    public void showScoresTable() {
        ConstraintLayout constraintLayout = findViewById(R.id.scoreConstraint);
        for (int i = 0; i < constraintLayout.getChildCount(); i++) {
            constraintLayout.getChildAt(i).setVisibility(View.GONE);
        }

        List<Score> worldScores = WorldScoresHandler.getScores();

        for (int i = 0; i < min(worldScores.size(), constraintLayout.getChildCount()); i++) {
            try {
                System.out.println(worldScores.get(i).getName() + " - " + worldScores.get(i).getValue());
                LinearLayout linearLayout = (LinearLayout) constraintLayout.getChildAt(i);
                linearLayout.setVisibility(View.VISIBLE);
                ((TextView) linearLayout.getChildAt(1)).setText(worldScores.get(i).getName());
                ((TextView) linearLayout.getChildAt(2)).setText(worldScores.get(i).getValue() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void back(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}