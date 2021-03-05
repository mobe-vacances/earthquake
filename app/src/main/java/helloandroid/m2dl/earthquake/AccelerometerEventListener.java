package helloandroid.m2dl.earthquake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class AccelerometerEventListener implements SensorEventListener {

    /**
     * The GameView, on which the listener will act on
     */
    private GameView gameView;

    /**
     * The orientation can stay the same if the device is on a flat surface.
     * This offset can vary.
     */
    public static float MAGNETIC_OFFSET = 0.5f;


    public AccelerometerEventListener(GameView gameView) {
        this.gameView = gameView;
    }


    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float[] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                float magField_x = values[0];
                float magField_y = values[1];
                String res = "";
                if (absolute(magField_x) > absolute(magField_y)) {
                    if (magField_x > MAGNETIC_OFFSET) {
                        res += "gauche";
                    } else if (magField_x < MAGNETIC_OFFSET) {
                        res += "droite";
                    }
                } else {
                    if (magField_y > MAGNETIC_OFFSET) {
                        res += "bas";
                    } else if (magField_y < MAGNETIC_OFFSET){
                        res += "haut";
                    }
                }

                System.out.println(res);
            }
        }
    }


    private float absolute(float value) {
        if(value < 0) {
            return -value;
        }
        return value;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int i) {
        // on ne fait rien pour l'instant
    }
}
