package helloandroid.m2dl.earthquake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener  {
    private GameThread thread;
    private int score = 0;
    private int x=0;
    private int updateValue = 10;
    private final int DEFAULT_COOLDOWN_VALUE = 200;
    private Handler handlerBulletTime;

    private CooldownManager cooldownManager;

    private Collection<PointCustom> points = new ArrayList<>();

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        cooldownManager = new CooldownManager();
        // Ajoute une interface de rappel pour ce titulaire.
        getHolder().addCallback(this);
        // Création thread en fornissant un accès et un contrôle sur la surface sous-jacente de cette SurfaceView.
        thread = new GameThread(getHolder(), this);
        //Défini si cette vue peut recevoir le focus.
        setFocusable(true);
        setOnTouchListener(this);
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

    public void update(){
        x = x + updateValue;
        if(x + 100 >=  MainActivity.sharedPref.getInt("screen_width",300)){
            System.out.println("GAME OVER");
            thread.setRunning(false);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //sharedPreferences.getInt("valeur_y",0)
        super.draw(canvas);
        if (canvas != null) {
            if (MainActivity.sharedPref.getBoolean("running", true)) {
                canvas.drawColor(Color.WHITE);
                Paint paint = new Paint();
                paint.setColor(Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                canvas.drawRect(x + 10, 500, x + 100, 700, paint);

                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                cooldownManager.drawBulletTimeIndicator(canvas, wm);


                Display display = wm.getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                Paint line = new Paint();
                line.setColor(Color.BLACK);
                canvas.drawLine(0,200,width,200,line);

                Paint text = new Paint();
                text.setColor(Color.BLACK);
                text.setTextSize(64);
                canvas.drawText("Score :",40,80,text);
                canvas.drawText(String.valueOf(score) ,40,156,text);


            }
        } else {
            canvas.drawColor(Color.BLACK);
            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setTextSize(150);
            paint.setTextAlign(Paint.Align.CENTER);

            int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
            canvas.drawText("Game Over", canvas.getWidth() / 2, yPos, paint);

            paint.setTextSize(50);
            canvas.drawText("score : ", canvas.getWidth() / 2, yPos + 100, paint);
        }
    }



    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN && cooldownManager.isBulletTimeDispo()) {
            cooldownManager.setBulletTimeDispo(false);
            updateValue = 2;
            handlerBulletTime = new Handler();
            handlerBulletTime.postDelayed(setUpdateValueToTenWithTimeout,5000);
        }
        return true;
    }


    private Runnable setUpdateValueToTenWithTimeout = new Runnable() {
        @Override
        public void run() {
            updateValue = 10;
            cooldownManager.setBulletTimeDispo(true);
            cooldownManager.setCooldownBulletTimeProgress(DEFAULT_COOLDOWN_VALUE);
        }
    };


    public void addPoint() {
        this.points.add(new PointCustom());
    }
}
