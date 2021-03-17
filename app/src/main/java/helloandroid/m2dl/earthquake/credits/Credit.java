package helloandroid.m2dl.earthquake.credits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.main_menu.MainMenu;

public class Credit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
    }


    public void backMenu(View view) {
        Intent intent = new Intent(Credit.this, MainMenu.class);
        startActivity(intent);
    }
}