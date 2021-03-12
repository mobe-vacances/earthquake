package helloandroid.m2dl.earthquake.game.background;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import helloandroid.m2dl.earthquake.game.mobengine.BaseSensorEventListener;

public class LightEventListener extends BaseSensorEventListener {

    private float maxLight = 0.0f;

    private Background background;

    public LightEventListener(Sensor sensor, Background background) {
        super(sensor);
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
