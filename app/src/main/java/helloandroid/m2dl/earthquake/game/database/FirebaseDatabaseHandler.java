package helloandroid.m2dl.earthquake.game.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import helloandroid.m2dl.earthquake.game_menu.GameOver;

public class FirebaseDatabaseHandler {

    private GameOver context;

    private FirebaseDatabase database;

    public FirebaseDatabaseHandler(GameOver context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
    }

    public void getAllScores(String user, String id, int score) {
        DatabaseReference scoresReference = database.getReference().child("scores");
        final Map<String, Integer> scores = new HashMap<>();
        scoresReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scores.clear();
                for (DataSnapshot element : snapshot.getChildren()) {
                    scores.put(element.getKey(), Integer.parseInt(element.getValue().toString()));
                }
                context.worldScore = scores;
                context.worldScore.put(id + '@' + user, score);
                context.showScoresTable();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addOrUpdateScore(String user, String id, int score) {
        DatabaseReference scoresReference = database.getReference().child("scores");
        scoresReference.child(id + '@' + user).setValue(score);
    }
}
