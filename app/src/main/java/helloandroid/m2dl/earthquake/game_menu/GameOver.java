package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import helloandroid.m2dl.earthquake.R;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        fillScoresTable();
    }

    private void fillScoresTable() {
        LinearLayout scoresTable = findViewById(R.id.scoresLayout);
        ConstraintLayout scoresLayout = (ConstraintLayout) scoresTable.getChildAt(0);

        for(int i = 0; i < scoresLayout.getChildCount(); i++) {
            LinearLayout line = (LinearLayout) scoresLayout.getChildAt(i);
            ConstraintLayout cr = (ConstraintLayout) line.getChildAt(0);
            TextView name = (TextView) cr.getChildAt(1);
            TextView score = (TextView) cr.getChildAt(2);

            name.setText("Name " + i);
            score.setText("Score " + i);
        }
    }

    public void back(View view){
        Intent intent = new Intent(GameOver.this, MainMenu.class);
        startActivity(intent);
    }
}