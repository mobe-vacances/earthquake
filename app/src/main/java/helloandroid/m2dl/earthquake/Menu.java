package helloandroid.m2dl.earthquake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
    }

    public void lauchGame(View view){
        Intent intent = new Intent(Menu.this, MainActivity.class);
        startActivity(intent);

    }
}