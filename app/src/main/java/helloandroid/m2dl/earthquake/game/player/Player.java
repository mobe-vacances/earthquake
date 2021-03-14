package helloandroid.m2dl.earthquake.game.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.geometry.Circle;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Updatable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.BitmapStore;
import helloandroid.m2dl.earthquake.game.mobengine.statics.DisplayScale;

public class Player implements Drawable, Updatable {

    private final Circle playerCircle = new Circle(new Rect(
            DisplayScale.getRect().centerX() - GameConstants.PLAYER_SIZE / 2,
            DisplayScale.getRect().centerY() - GameConstants.PLAYER_SIZE / 2,
            DisplayScale.getRect().centerX() + GameConstants.PLAYER_SIZE / 2,
            DisplayScale.getRect().centerY() + GameConstants.PLAYER_SIZE / 2
    ));

    private double xAcceleration = 0.0;
    private double yAcceleration = 0.0;

    private int rotation = 0;

    @Override
    public int getZIndex() {
        return GameConstants.PLAYER_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix rotator = new Matrix();
        rotator.postRotate(rotation,playerCircle.getEnclosingRect().width()/2.0f,playerCircle.getEnclosingRect().height()/2.0f);
        rotator.postTranslate(playerCircle.getEnclosingRect().left, playerCircle.getEnclosingRect().top);
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(BitmapStore.getBitmap(R.drawable.world), playerCircle.getEnclosingRect().width(), playerCircle.getEnclosingRect().height(), false),
                rotator,
                null
        );
        
    }

    @Override
    public void update(int delta) {
        rotation = (int) ((rotation + GameConstants.PLAYER_ROTATION_SPEED*delta) % 360);

        double direction = Math.atan2(yAcceleration,xAcceleration);
        double speed = Math.min(Math.sqrt(xAcceleration*xAcceleration + yAcceleration*yAcceleration),GameConstants.PLAYER_MAX_SPEED);

        playerCircle.getEnclosingRect().offsetTo(
                (int)(playerCircle.getEnclosingRect().left + speed*Math.cos(direction)*delta),
                (int)(playerCircle.getEnclosingRect().top + speed*Math.sin(direction)*delta)
        );

        if(!GameState.getGameRect().contains(playerCircle.getEnclosingRect())) {
            GameState.gameOver();
        }
    }

    public void setxAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public void setyAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public Circle getHitbox() {
        return playerCircle;
    }
}
