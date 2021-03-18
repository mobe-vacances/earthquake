package helloandroid.m2dl.earthquake.game.mobengine.utils;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorService {

    private static Vibrator vibrator;

    private static boolean vibrationsActive = true;

    public static void requestVibrator(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void click() {
        if(vibrationsActive && vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
        }
    }

    public static void heavyClick() {
        if(vibrationsActive && vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
        }
    }

    public static Vibrator get() {
        return vibrator;
    }

    public static boolean isVibrationsActive() {
        return vibrationsActive;
    }

    public static void setVibrationsActive(boolean vibrationsActive) {
        VibratorService.vibrationsActive = vibrationsActive;
    }
}
