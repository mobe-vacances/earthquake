package helloandroid.m2dl.earthquake.game_menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
    }

    public void lauchGame(View view){
        Intent intent = new Intent(MainMenu.this, GameActivity.class);
        startActivity(intent);

    }

    public void test(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("yeah");

        myRef.setValue("Pomme");
        System.out.println(FirebaseDatabase.getInstance().getApp().getName());

        TextView txtView = (TextView) ((Activity)this).findViewById(R.id.textView);
        txtView.setText("test");
    }


}