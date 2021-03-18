package helloandroid.m2dl.earthquake.game.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import helloandroid.m2dl.earthquake.scores.Score;

public class HighscoreHandler {

    private static Score highScore;

    private static DatabaseReference userHighScoreReference;

    public static void init() {
        userHighScoreReference = FirebaseDatabase.getInstance().getReference().child("scores").child(FirebaseInstallationService.getId());
        userHighScoreReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    highScore = snapshot.getValue(Score.class);
                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void updateHighScore(Score score) {
        if(highScore == null || highScore.getValue() < score.getValue()) {
            userHighScoreReference.setValue(score);
        }
    }

    public static Score getHighScore() {
        return highScore;
    }
}
