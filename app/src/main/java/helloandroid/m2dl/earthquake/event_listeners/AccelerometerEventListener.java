package helloandroid.m2dl.earthquake.event_listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import helloandroid.m2dl.earthquake.game.GameView;

public class AccelerometerEventListener implements SensorEventListener {

    /**
     * The GameView, on which the listener will act on
     */
    private final GameView gameView;

    /**
     * This multiplier allows the player to be more sensitive to gravity.
     * By increasing this multiplier, the player will move faster.
     */
    public static int GRAVITY_MULTIPLIER = 5;


    public AccelerometerEventListener(GameView gameView) {
        this.gameView = gameView;
    }


    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float[] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                gameView.player.directionX = - ( values[0] * GRAVITY_MULTIPLIER);
                gameView.player.directionY = values[1] * GRAVITY_MULTIPLIER;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int i) {
        // on ne fait rien pour l'instant
    }
}
