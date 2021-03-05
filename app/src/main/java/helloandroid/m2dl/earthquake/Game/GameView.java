package helloandroid.m2dl.earthquake.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import helloandroid.m2dl.earthquake.BitmapRepository;
import helloandroid.m2dl.earthquake.Direction;
import helloandroid.m2dl.earthquake.MainActivity;
import helloandroid.m2dl.earthquake.Obstacle.Crack;
import helloandroid.m2dl.earthquake.Obstacle.Obstacles;
import helloandroid.m2dl.earthquake.Player;
import helloandroid.m2dl.earthquake.R;

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
        Point initialPosition = new Point(MainActivity.sharedPref.getInt("screen_width",300) / 2, MainActivity.sharedPref.getInt("screen_height",300) / 2);
        player = new Player(initialPosition, Direction.RIGHT, 40);
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

        Point pos = player.getPosition();
        if(
                pos.x + player.getWidth() >=  MainActivity.sharedPref.getInt("screen_width",300) ||
                        pos.x < 0 ||
                        pos.y + player.getHeight() >=  MainActivity.sharedPref.getInt("screen_height",300) ||
                        pos.y < 0
        ){
            thread.setRunning(false);
        }

        if(obstacles.touch(player)){
                thread.setRunning(false);

        }

        playerRotation = (playerRotation + 30) % 360;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if(MainActivity.sharedPref.getBoolean("running",true)){
                canvas.drawColor(backgroundColor);

                addGround(canvas);

                Matrix rotator = new Matrix();
                rotator.postRotate(playerRotation,player.getHeight()/2,player.getWidth()/2);
                rotator.postTranslate(player.getPosition().x, player.getPosition().y);
                canvas.drawBitmap(
                        Bitmap.createScaledBitmap(bitmapRepository.getBitmap(R.drawable.hero), player.getWidth(), player.getHeight(), false),
                        rotator,
                        null
                );


                addObstacle(canvas);


            } else {
                canvas.drawColor(Color.BLACK);
                Paint paint = new Paint();
                paint.setColor(Color.YELLOW);
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.helmet);
                int  left = canvas.getWidth()/2;
                canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 400, 400 , false),left - 200, canvas.getWidth()/2-30,null); // 24 is the height of image

                canvas.drawText("Tu as perdu !",left,yPos,paint);

                paint.setTextSize(50);
                canvas.drawText("score : ",left,yPos+100,paint);
            }

        }
    }

    private void addGround(Canvas canvas) {
        int sizeGround = 600;
        int numberForWidth = MainActivity.sharedPref.getInt("screen_width",300) / sizeGround + 1;
        int numberForHeight = MainActivity.sharedPref.getInt("screen_height",300) / sizeGround + 1;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),  R.drawable.ground2);
        for(int i = 0; i < numberForWidth; i++){
            for(int j = 0; j < numberForHeight; j++){
                canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 600, 600, false), i*sizeGround, j*sizeGround,null);
            }
        }

    }

    private void addObstacle(Canvas canvas) {
        obstacles.evolObstacle();
        for (Crack p : obstacles.getCracks()) {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(),  p.getImg());
            canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 100, 100, false), p.x,p.y,null);
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
