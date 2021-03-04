package helloandroid.m2dl.earthquake;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private GameView gameView;
    private Handler handler = new Handler();
    static SharedPreferences sharedPref;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            gameView.addPoint();
            handler.postDelayed(this,100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //masquer toutes les décorations d'écran (telles que la barre d'état) pendant que cette fenêtre est affichée
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //désactivant le titre en haut de l'écran
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        getSizeScreen();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("valeur_y",  (sharedPref.getInt("valeur_y", 0)+ 100) % 400);
        editor.apply();

        gameView = new GameView(this, sharedPref);

        handler.postDelayed(runnable,100);

        setContentView(gameView);
    }

    private void getSizeScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        android.graphics.Point size = new Point();
        display.getSize(size);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("screen_width", size.x);
        editor.putInt("screen_height", size.y);
        editor.putBoolean("running", false);
        editor.apply();
    }
}