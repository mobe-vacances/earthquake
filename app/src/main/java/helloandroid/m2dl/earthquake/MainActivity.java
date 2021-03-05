package helloandroid.m2dl.earthquake;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private GameView gameView;
    private Handler handler = new Handler();
    static SharedPreferences sharedPref;

    private Sensor mMagneticField;
    private AccelerometerEventListener accelerometerEventListener;
    private SensorManager sensorManager;
    private Sensor light;
    private LightEventListener lightEventListener;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
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


        gameView = new GameView(this, sharedPref);


        //handler.postDelayed(runnable,100);

        setContentView(gameView);

        //Initialize the accelerometer sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        mMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometerEventListener = new AccelerometerEventListener(gameView);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightEventListener = new LightEventListener(gameView);
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

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerEventListener, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(lightEventListener, light, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
        sensorManager.unregisterListener(accelerometerEventListener);
    }
}