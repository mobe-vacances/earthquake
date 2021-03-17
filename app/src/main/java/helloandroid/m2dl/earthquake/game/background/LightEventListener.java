package helloandroid.m2dl.earthquake.game.background;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import helloandroid.m2dl.earthquake.game.mobengine.sensors.BaseSensorEventListener;

public class LightEventListener extends BaseSensorEventListener {

    private float maxLight = 0.0f;

    private final Background background;

    public LightEventListener(Background background) {
        super(Sensor.TYPE_LIGHT);
        this.background = background;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if(event.values[0] > maxLight) {
                maxLight = event.values[0];
            }
            background.setTargetIntensity(event.values[0] / maxLight);
        }
    }
}
