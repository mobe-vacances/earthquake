package helloandroid.m2dl.earthquake.game.player;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.player.Player;
import helloandroid.m2dl.earthquake.game.mobengine.BaseSensorEventListener;

public class AccelerometerEventListener extends BaseSensorEventListener {

    private final Player player;

    public AccelerometerEventListener(Sensor sensor, Player player) {
        super(sensor);
        this.player = player;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            player.setxAcceleration(
                   -1*event.values[0]*GameConstants.ACCELEROMETER_SENSIBILITY
            );

            player.setyAcceleration(
                    event.values[1]*GameConstants.ACCELEROMETER_SENSIBILITY
            );
        }
    }
}
