package helloandroid.m2dl.earthquake.game.mobengine.utils;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorService {

    private static Vibrator vibrator;
    private static boolean animationActive;

    public VibratorService(){
        this.animationActive = true;
    }

    public static void setAnimationActive(boolean act){
        animationActive = act;
    }

    public static boolean getAnimationActive(){
        return animationActive;
    }

    public static void requestVibrator(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void click() {
        if(animationActive && vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK));
        }
    }

    public static void heavyClick() {
        if(animationActive && vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK));
        }
    }

    public static Vibrator get() {
        return vibrator;
    }
}
