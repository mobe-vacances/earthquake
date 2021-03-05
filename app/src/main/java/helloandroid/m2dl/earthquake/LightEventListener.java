package helloandroid.m2dl.earthquake;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class LightEventListener implements SensorEventListener {

    private GameView gameView;
    private float maxLight = 0;

    public LightEventListener(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float currentLight = event.values[0];
        if(currentLight > maxLight) {
            maxLight = currentLight;
        }
        int level = (int) (255 * currentLight / maxLight);
        gameView.setBackgroundColor(Color.rgb((int) (0.8*level), (int) (0.8*level), level));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
