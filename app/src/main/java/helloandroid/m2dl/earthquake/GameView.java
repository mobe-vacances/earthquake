package helloandroid.m2dl.earthquake;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static android.content.Context.SENSOR_SERVICE;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    public Player player;
    private Obstacles obstacles = new Obstacles();
    private BitmapRepository bitmapRepository;

    private int backgroundColor;

    private int playerRotation = 0;

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        // Ajoute une interface de rappel pour ce titulaire.
        getHolder().addCallback(this);
        Random random = new Random();
        android.graphics.Point initialPosition = new android.graphics.Point(
                MainActivity.sharedPref.getInt("screen_width",300) / 2 - 50,
                MainActivity.sharedPref.getInt("screen_height",300) / 2 - 50
        );
        player = new Player(initialPosition, Direction.RIGHT, 10);
        // Création thread en fornissant un accès et un contrôle sur la surface sous-jacente de cette SurfaceView.
        thread = new GameThread(getHolder(), this);
        //Défini si cette vue peut recevoir le focus.
        setFocusable(true);

        bitmapRepository = new BitmapRepository(getResources());
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
        player.updatePosition();
        android.graphics.Point pos = player.getPosition();
        if(
                pos.x + 100 >=  MainActivity.sharedPref.getInt("screen_width",300) ||
                        pos.x < 0 ||
                        pos.y + 100 >=  MainActivity.sharedPref.getInt("screen_height",300) ||
                        pos.y < 0
        ){
            System.out.println("GAME OVER");
            thread.setRunning(false);
        }

        playerRotation = (playerRotation + 30) % 360;
        obstacles.evolObstacle();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if(MainActivity.sharedPref.getBoolean("running",true)){
                canvas.drawColor(backgroundColor);
                Matrix rotator = new Matrix();

                rotator.postRotate(playerRotation,50,50);
                rotator.postTranslate(player.getPosition().x, player.getPosition().y);
                canvas.drawBitmap(
                        Bitmap.createScaledBitmap(bitmapRepository.getBitmap(R.drawable.smile), 100, 100, false),
                        rotator,
                        null
                );
                for (Crack p : obstacles.getCracks()) {
                    Bitmap bmp = bitmapRepository.getBitmap(p.getImg());
                    canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false), p.getX(),p.getY(),null);
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

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
