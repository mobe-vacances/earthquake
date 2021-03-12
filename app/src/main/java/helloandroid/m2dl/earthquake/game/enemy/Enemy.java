package helloandroid.m2dl.earthquake.game.enemy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.mobengine.RandomService;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Updatable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.BitmapStore;

public class Enemy implements Drawable, Updatable {

    private Rect rect;

    private double size = 1.0;

    private double xSpeed;
    private double ySpeed;

    private int rotation = 0;

    private final double rotationSpeed = RandomService.nextRelativeDouble();

    private EnemyState state = EnemyState.INOFFENSIVE;

    private final Rect playerHitbox;

    public Enemy(Rect playerHitbox, Rect rect) {
        this.playerHitbox = playerHitbox;
        this.rect = rect;

        xSpeed = (playerHitbox.exactCenterX() - rect.exactCenterX()) * GameConstants.ENEMY_INITIAL_SPEED;
        ySpeed = (playerHitbox.exactCenterY() - rect.exactCenterY()) * GameConstants.ENEMY_INITIAL_SPEED;

        Handler handler = new Handler();
        handler.postDelayed(
                () -> this.state = EnemyState.DANGER,
                GameConstants.ENEMY_GROWING_TIME
        );
    }

    @Override
    public int getZIndex() {
        return GameConstants.ENEMY_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix rotator = new Matrix();
        rotator.postRotate(rotation,rect.width()/2.0f,rect.height()/2.0f);
        rotator.postTranslate(rect.left, rect.top);
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(
                        BitmapStore.getBitmap(state == EnemyState.INOFFENSIVE ? R.drawable.coronavirus_safe : R.drawable.coronavirus),
                        rect.width(),
                        rect.height(),
                        false),
                rotator,
                null
        );
    }

    @Override
    public void update(int delta) {
        rotation = (int) ((rotation + rotationSpeed*GameConstants.ENEMY_ROTATION_SPEED*delta) % 360);

        rect.offsetTo(
                (int)(rect.left + xSpeed* BulletTime.getBulletTimeMultiplier()*delta),
                (int)(rect.top + ySpeed*BulletTime.getBulletTimeMultiplier()*delta)
        );

        if(size < GameConstants.ENEMY_SIZE) {
            size = Math.min(size + GameConstants.ENEMY_GROWING_SPEED*delta, GameConstants.ENEMY_SIZE);
            rect.inset((int)(rect.width() - size)/2, (int)(rect.height() - size)/2);
        }

        if(state == EnemyState.DANGER && Rect.intersects(rect, playerHitbox)) {
            GameState.gameOver();
        }

        if(state == EnemyState.DANGER && !GameState.getGameRect().contains(rect)) {
            GameEngine.removeGameElement(this);
        }
    }

}
