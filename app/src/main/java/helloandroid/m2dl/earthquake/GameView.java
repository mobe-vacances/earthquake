package helloandroid.m2dl.earthquake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private int x=0;
    private SharedPreferences sharedPreferences;

    private Collection<Point> points = new ArrayList<>();

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        this.sharedPreferences = sharedPreferences;
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        x = (x + 1) % 300;
    }

    @Override
    public void draw(Canvas canvas) {
        //sharedPreferences.getInt("valeur_y",0)
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));

            for(Point p : points) {
                p.setX((p.getX() + 1) % 1000);
                p.setY((p.getY() + 1) % 1000);

                canvas.drawRect(p.getX(),p.getY() , p.getX()+100, p.getY() +100, paint);

            }

        }
    }

    public void addPoint() {
        this.points.add(new Point());
    }
}
