package helloandroid.m2dl.earthquake.game.mobengine.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

import helloandroid.m2dl.earthquake.game.mobengine.sensors.SensorManagerService;

public abstract class BaseSensorEventListener implements SensorEventListener {

    private Sensor sensor;

    public BaseSensorEventListener(int type) {
        this.sensor = SensorManagerService.getDefaultSensor(type);
    }

    public Sensor getSensor() {
        return sensor;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        this.sensor = sensor;
    }
}
