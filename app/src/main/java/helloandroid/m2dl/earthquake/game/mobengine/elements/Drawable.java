package helloandroid.m2dl.earthquake.game.mobengine.elements;

import android.graphics.Canvas;

public interface Drawable {

    int getZIndex();
    void draw(Canvas canvas);
}
