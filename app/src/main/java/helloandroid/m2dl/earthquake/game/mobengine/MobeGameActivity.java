package helloandroid.m2dl.earthquake.game.mobengine;

import android.app.Activity;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import helloandroid.m2dl.earthquake.game.mobengine.statics.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;
import helloandroid.m2dl.earthquake.game.mobengine.statics.SoundStore;


public abstract class MobeGameActivity extends Activity {

    protected abstract int[] getUsedSoundIds();

    protected abstract int[] getUsedBitmapsIds();

    private List<BaseSensorEventListener> baseSensorEventListeners;

    private SensorManager sensorManager;

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        DisplayScale.updateScale(size);

        SoundStore.createMediaPlayers(getUsedSoundIds(), this);

        BitmapStore.decodeBitmaps(getUsedBitmapsIds(), getResources());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        baseSensorEventListeners = new ArrayList<>();

        gameView = new GameView(this, new GameEngine());

        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        for (BaseSensorEventListener baseSensorEventListener : baseSensorEventListeners) {
            sensorManager.registerListener(baseSensorEventListener, baseSensorEventListener.getSensor(), SensorManager.SENSOR_DELAY_GAME);
        }

        GameEngine.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (BaseSensorEventListener baseSensorEventListener : baseSensorEventListeners) {
            sensorManager.unregisterListener(baseSensorEventListener);
        }
        GameEngine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundStore.releaseMediaPlayers();
    }

    protected List<BaseSensorEventListener> getBaseSensorEventListeners() {
        return baseSensorEventListeners;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public GameView getGameView() {
        return gameView;
    }
}
