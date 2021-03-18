package helloandroid.m2dl.earthquake.game_over;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.database.HighscoreHandler;
import helloandroid.m2dl.earthquake.game.database.WorldScoresHandler;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.scores.Score;

import static java.lang.Math.min;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;

public class GameOverActivity extends AppCompatActivity {

    private class ScoreAdapter extends ArrayAdapter {private Context mContext;

        private List<Score> scores;

        public ScoreAdapter(@NonNull Context context, List<Score> list) {
            super(context, 0 , list);
            mContext = context;
            scores = list;
            System.out.println(scores.size());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null) {
                listItem = LayoutInflater.from(mContext).inflate(R.layout.score_item, parent, false);
            }

            Score score = scores.get(position);
            ((TextView) listItem.findViewById(R.id.name)).setText(score.getName());
            ((TextView) listItem.findViewById(R.id.score)).setText(String.valueOf(score.getValue()));
            return listItem;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Score score = new Score(getUsername(), GameState.getScore());
        HighscoreHandler.updateHighScore(score);

        showScore();
        showScoresTable();

        SoundStore.playGameOverSoundMediaPlayer();
    }

    private int getHighestScore() {
        if(HighscoreHandler.getHighScore() != null) {
            return Math.max(GameState.getScore(), HighscoreHandler.getHighScore().getValue());
        }

        return GameState.getScore();
    }

    private String getUsername() {
        return getSharedPreferences(GameConstants.APP_SHARED_PREFERENCES_KEY, MODE_PRIVATE).getString("username", getResources().getString(R.string.default_player_name));
    }

    private void showScore() {
        ((TextView)findViewById(R.id.currentScore)).setText("Votre score : " + GameState.getScore());
        ((TextView)findViewById(R.id.highestScore)).setText("Record : " + getHighestScore());
    }

    public void showScoresTable() {
        List<Score> worldScores = WorldScoresHandler.getScores();
        ListView listView = findViewById(R.id.scores);
        ScoreAdapter arrayAdapter = new ScoreAdapter(this, worldScores);
        listView.setAdapter(arrayAdapter);

    }

    public void back(View view){
        SoundStore.stopGameOverSoundMediaPlayer();
        SoundStore.playClickMediaPlayer();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}