package helloandroid.m2dl.earthquake.game.mobengine.event_listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public abstract class BaseSensorEventListener implements SensorEventListener {

    private Sensor sensor;

    public BaseSensorEventListener(Sensor sensor) {
        this.sensor = sensor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        this.sensor = sensor;
    }
}
