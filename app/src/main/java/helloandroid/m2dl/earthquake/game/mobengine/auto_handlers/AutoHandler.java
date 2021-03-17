package helloandroid.m2dl.earthquake.game.mobengine.auto_handlers;

import android.os.Handler;

import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;

public abstract class AutoHandler {

    private final Handler handler = new Handler();

    private final Runnable handle = () -> {
        if(GameEngine.isRunning()) {
            onTrigger();
        }
        handler.postDelayed(this.handle, getTriggerDelay());
    };

    protected abstract int getTriggerDelay();

    protected abstract void onTrigger();

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    public void start() {
        handler.postDelayed(handle, getTriggerDelay());
    }
}
