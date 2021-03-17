package helloandroid.m2dl.earthquake.game.enemy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.mobengine.utils.RandomService;
import helloandroid.m2dl.earthquake.game.GameConstants;
import helloandroid.m2dl.earthquake.game.geometry.Circle;
import helloandroid.m2dl.earthquake.game.particle.Particle;
import helloandroid.m2dl.earthquake.game.player.Player;
import helloandroid.m2dl.earthquake.game.state.GameState;
import helloandroid.m2dl.earthquake.game.bullet_time.BulletTime;
import helloandroid.m2dl.earthquake.game.mobengine.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.core.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.core.Updatable;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.BitmapStore;

public class Enemy implements Drawable, Updatable {

    private Circle circle;

    private double size = 1.0;

    private double xSpeed;
    private double ySpeed;

    private int rotation = 0;

    private final double rotationSpeed = RandomService.nextRelativeDouble();

    private EnemyState state = EnemyState.INOFFENSIVE;

    private final Player player;

    public Enemy(Player player, Rect rect) {
        this.player = player;
        this.circle = new Circle(rect);

        xSpeed = (player.getHitbox().getEnclosingRect().exactCenterX() - rect.exactCenterX()) * GameConstants.ENEMY_INITIAL_SPEED;
        ySpeed = (player.getHitbox().getEnclosingRect().exactCenterY() - rect.exactCenterY()) * GameConstants.ENEMY_INITIAL_SPEED;
    }

    @Override
    public int getZIndex() {
        return GameConstants.ENEMY_Z_INDEX;
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix rotator = new Matrix();
        rotator.postRotate(rotation,circle.getEnclosingRect().width()/2.0f,circle.getEnclosingRect().height()/2.0f);
        rotator.postTranslate(circle.getEnclosingRect().left, circle.getEnclosingRect().top);
        canvas.drawBitmap(
                Bitmap.createScaledBitmap(
                        BitmapStore.getBitmap(state == EnemyState.INOFFENSIVE ? R.drawable.coronavirus_safe : R.drawable.coronavirus),
                        circle.getEnclosingRect().width(),
                        circle.getEnclosingRect().height(),
                        false),
                rotator,
                null
        );
    }

    @Override
    public void update(int delta) {
        rotation = (int) ((rotation + rotationSpeed*GameConstants.ENEMY_ROTATION_SPEED*delta) % 360);

        circle.getEnclosingRect().offsetTo(
                (int)(circle.getEnclosingRect().left + xSpeed* BulletTime.getBulletTimeMultiplier()*delta),
                (int)(circle.getEnclosingRect().top + ySpeed*BulletTime.getBulletTimeMultiplier()*delta)
        );

        if(state == EnemyState.INOFFENSIVE && size < GameConstants.ENEMY_SIZE) {
            size = Math.min(size + GameConstants.ENEMY_GROWING_SPEED*delta, GameConstants.ENEMY_SIZE);
            circle.getEnclosingRect().inset((int)(circle.getEnclosingRect().width() - size)/2, (int)(circle.getEnclosingRect().height() - size)/2);
            if (size >= GameConstants.ENEMY_SIZE) {
                state = EnemyState.DANGER;
            }
        }

        if(state == EnemyState.DANGER && Circle.intersects(circle, player.getHitbox())) {
            player.die();
        }

        if(state == EnemyState.DANGER && !GameState.getGameRect().contains(circle.getEnclosingRect())) {
            spawnEnemyDeathParticles(circle.getEnclosingRect().exactCenterX(), circle.getEnclosingRect().exactCenterY());
            GameEngine.removeGameElement(this);
        }
    }

    private static void spawnEnemyDeathParticles(float x, float y) {
        for (int i = RandomService.nextIntBetween(GameConstants.ENEMY_DEATH_PARTICLE_MIN_NUMBER, GameConstants.ENEMY_DEATH_PARTICLE_MAX_NUMBER); i > 0; i--) {
            GameEngine.addGameElements(new Particle(
                    x,
                    y,
                    GameConstants.ENEMY_DEATH_PARTICLE_RADIUS,
                    RandomService.nextDoubleBetween(GameConstants.ENEMY_DEATH_PARTICLE_MIN_SPEED, GameConstants.ENEMY_DEATH_PARTICLE_MAX_SPEED),
                    2*Math.PI*RandomService.get().nextDouble(),
                    GameConstants.ENEMY_DEATH_PARTICLE_COLORS[RandomService.get().nextInt(GameConstants.ENEMY_DEATH_PARTICLE_COLORS.length)],
                    true
            ));
        }
    }
}
