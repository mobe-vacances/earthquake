package helloandroid.m2dl.earthquake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    private int x=0;
    private Obstacle obstacle = new Obstacle();

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        // Ajoute une interface de rappel pour ce titulaire.
        getHolder().addCallback(this);
        // Création thread en fornissant un accès et un contrôle sur la surface sous-jacente de cette SurfaceView.
        thread = new GameThread(getHolder(), this);
        //Défini si cette vue peut recevoir le focus.
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

    public void update(){
        x = x + 10;
        if(x + 100 >=  MainActivity.sharedPref.getInt("screen_width",300)){
            System.out.println("GAME OVER");
            thread.setRunning(false);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if(MainActivity.sharedPref.getBoolean("running",true)){
                canvas.drawColor(Color.WHITE);
                Paint paint = new Paint();
                paint.setColor(Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                canvas.drawRect(x + 10, 0, x + 100, 100, paint);

                obstacle.evolObstacle();
                for (Crack p : obstacle.getCracks()) {
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),  p.getImg());
                    canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false), p.getX(),p.getY(),null); // 24 is the height of image
                }

            } else {
                canvas.drawColor(Color.BLACK);
                Paint paint = new Paint();
                paint.setColor(Color.YELLOW);
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over",canvas.getWidth()/2,yPos,paint);

                paint.setTextSize(50);
                canvas.drawText("score : ",canvas.getWidth()/2,yPos+100,paint);
            }

        }
    }

}
