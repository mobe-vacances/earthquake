package helloandroid.m2dl.earthquake.Game;

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

import helloandroid.m2dl.earthquake.Direction;
import helloandroid.m2dl.earthquake.MainActivity;
import helloandroid.m2dl.earthquake.Obstacle.Crack;
import helloandroid.m2dl.earthquake.Obstacle.Obstacles;
import helloandroid.m2dl.earthquake.Player;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread thread;
    public Player player;
    private Obstacles obstacles = new Obstacles();

    private int backgroundColor;

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        // Ajoute une interface de rappel pour ce titulaire.
        getHolder().addCallback(this);
        Random random = new Random();
        android.graphics.Point initialPosition = new android.graphics.Point(random.nextInt(100), random.nextInt(100));
        player = new Player(initialPosition, Direction.RIGHT, 10);
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
        player.updatePosition();
        android.graphics.Point pos = player.getPosition();
        if(pos.x + 100 >=  MainActivity.sharedPref.getInt("screen_width",300) || pos.y + 100 >=  MainActivity.sharedPref.getInt("screen_height",300)){
            System.out.println("GAME OVER");
            thread.setRunning(false);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if(MainActivity.sharedPref.getBoolean("running",true)){
                canvas.drawColor(backgroundColor);
                Paint paint = new Paint();
                paint.setColor(Color.rgb(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                canvas.drawRect(player.getPosition().x, player.getPosition().y, player.getPosition().x + 100, player.getPosition().y + 100, paint);
                obstacles.evolObstacle();
                for (Crack p : obstacles.getCracks()) {
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),  p.getImg());
                    canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false), p.x,p.y,null); // 24 is the height of image
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
