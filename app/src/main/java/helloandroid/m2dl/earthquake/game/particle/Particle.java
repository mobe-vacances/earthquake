package helloandroid.m2dl.earthquake.game.particle;

import android.graphics.Canvas;
import android.graphics.Paint;

import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.core.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.core.Updatable;
import helloandroid.m2dl.earthquake.game.state.GameState;

public class Particle implements Drawable, Updatable {

    private final Paint paint;

    private final float radius;

    private float x;
    private float y;

    private double speed;
    private final double direction;

    private final boolean disappearWhenStationary;

    public Particle(float x, float y, float radius, double speed, double direction, int color, boolean disappearWhenStationary) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
        this.direction = direction;
        this.paint = new Paint();
        this.paint.setColor(color);
        this.disappearWhenStationary = disappearWhenStationary;
    }

    @Override
    public int getZIndex() {
        return GameConstants.PARTICLE_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void update(int delta) {
        speed = Math.max(speed - GameConstants.PARTICLE_DECELERATION*delta,0);

        x += speed*Math.cos(direction)*delta;
        y += speed*Math.sin(direction)*delta;

        if(disappearWhenStationary && speed == 0 || !GameState.getGameRect().intersects((int)(x - radius),(int)(y - radius), (int)(x + radius), (int)(y + radius))) {
            GameEngine.removeGameElement(this);
        }
    }
}
