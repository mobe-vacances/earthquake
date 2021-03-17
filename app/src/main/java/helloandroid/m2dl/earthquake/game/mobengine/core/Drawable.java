package helloandroid.m2dl.earthquake.game.mobengine.core;

import android.graphics.Canvas;

public interface Drawable {

    int getZIndex();
    void draw(Canvas canvas);
}
