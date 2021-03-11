package helloandroid.m2dl.earthquake.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.util.Random;

import helloandroid.m2dl.earthquake.entity.Obstacle.Obstacle;
import helloandroid.m2dl.earthquake.game_controllers.BitmapRepository;
import helloandroid.m2dl.earthquake.game_controllers.CooldownManager;
import helloandroid.m2dl.earthquake.MainActivity;
import helloandroid.m2dl.earthquake.entity.Obstacle.Obstacles;
import helloandroid.m2dl.earthquake.entity.player.Player;
import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game_controllers.ScoreCalc;

public class GameView extends SurfaceView implements SurfaceHolder.Callback , View.OnTouchListener  {


    public static final int HEIGHT_BARRE = 100;
    public static final int NB_BAD_OBSTACLES = 5;
    public static final int NB_GOOD_OBSTACLES = 5;

    private GameThread thread;
    public Player player;
    private ScoreCalc score;
    private Obstacles badObstacles = new Obstacles(NB_BAD_OBSTACLES);
    private Obstacles goodObstacles = new Obstacles(NB_GOOD_OBSTACLES);
    private BitmapRepository bitmapRepository;
    private CooldownManager cooldownManager;
    private final int DEFAULT_COOLDOWN_VALUE = 200;
    private static final int HEIGHT_TEXT_BARRE = 20;


    private int level;

    private int backgroundColor;

    public GameView(Context context, SharedPreferences sharedPreferences) {
        super(context);
        this.level=1;

        // Ajoute une interface de rappel pour ce titulaire.
        getHolder().addCallback(this);
        Random random = new Random();
        Point initialPosition = new Point(MainActivity.sharedPref.getInt("screen_width",300) / 2, MainActivity.sharedPref.getInt("screen_height",300) / 2);
        player = new Player(initialPosition);
        // Création thread en fornissant un accès et un contrôle sur la surface sous-jacente de cette SurfaceView.
        thread = new GameThread(getHolder(), this);
        //Défini si cette vue peut recevoir le focus.
        setFocusable(true);

        cooldownManager = new CooldownManager(player,DEFAULT_COOLDOWN_VALUE);
        this.score = new ScoreCalc(player,this);
        setOnTouchListener(this);

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
                        pos.y < HEIGHT_BARRE
        ){
            thread.setRunning(false);
        }

        if(badObstacles.touch(player, true)){
                thread.setRunning(false);
        }

        if(goodObstacles.touch(player, false)){
            score.add(10);
        }

        player.rotate(10);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if(MainActivity.sharedPref.getBoolean("running",true)){
                canvas.drawColor(backgroundColor);

                //addGround(canvas);

                Matrix rotator = new Matrix();
                rotator.postRotate(player.getRotation(),player.getHeight()/2,player.getWidth()/2);
                rotator.postTranslate(player.getPosition().x, player.getPosition().y);
                canvas.drawBitmap(
                        Bitmap.createScaledBitmap(bitmapRepository.getBitmap(R.drawable.hero), player.getWidth(), player.getHeight(), false),
                        rotator,
                        null
                );
                addBarre(canvas);

                WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                cooldownManager.drawBulletTimeIndicator(canvas, wm);

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
                canvas.drawText("score : "+score.getScore(),left,yPos+100,paint);
            }

        }
    }

    private void addBarre(Canvas canvas) {
        Paint line = new Paint();
        line.setColor(Color.YELLOW);
        canvas.drawRect(0,0,MainActivity.sharedPref.getInt("screen_width",300),HEIGHT_BARRE,line);

        Paint textColor = new Paint();
        //GREEN => illisible imo
        textColor.setColor(Color.BLACK);
        textColor.setTextSize(HEIGHT_TEXT_BARRE);
        int textOffsetX = 40;
        int textOffsetY = (HEIGHT_BARRE / 2) + 10;
        String scoreText = "Score : ";
        canvas.drawText(scoreText,textOffsetX,textOffsetY,textColor);
        canvas.drawText(String.valueOf(score.getScore()) ,textOffsetX + (scoreText.length() * 10),textOffsetY,textColor);
    }

    private void addGround(Canvas canvas) {
        int sizeGround = 200;
        int numberForWidth = MainActivity.sharedPref.getInt("screen_width",300) / sizeGround + 1;
        int numberForHeight = MainActivity.sharedPref.getInt("screen_height",300) / sizeGround + 1;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),  R.drawable.ground2);
        for(int i = 0; i < numberForWidth; i++){
            for(int j = 0; j < numberForHeight; j++){
                canvas.drawBitmap(Bitmap.createScaledBitmap(bmp, 600, 600, false), i*sizeGround, /*j*sizeGround*/400*j,null);
            }
        }

    }

    private void addObstacle(Canvas canvas) {
        badObstacles.evolObstacle();
        for (Obstacle p : badObstacles.getObstacles()) {
            if(!p.isWithoutImage()) {
                canvas.drawBitmap(
                        Bitmap.createScaledBitmap(p.isDanger() ? bitmapRepository.getBitmap(R.drawable.crack_danger) : bitmapRepository.getBitmap(R.drawable.crack), Obstacles.WIDTH_ENNEMY, Obstacles.HEIGHT_ENNEMY, false),
                        p.x, p.y,
                        null
                );
            }
        }

        goodObstacles.evolObstacle();
        for (Obstacle p : goodObstacles.getObstacles()) {
            if(!p.isWithoutImage()) {
                canvas.drawBitmap(
                        Bitmap.createScaledBitmap(bitmapRepository.getBitmap(R.drawable.mask), Obstacles.WIDTH_ENNEMY, Obstacles.HEIGHT_ENNEMY, false),
                        p.x, p.y,
                        null
                );
            }
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN && cooldownManager.isBulletTimeDispo()) {
            cooldownManager.setBulletTimeDispo(false);
            cooldownManager.activateBulletTime(4);
        }
        return true;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Modifie le level, ainsi que la difficulté (en modifiant la vitesse du joueur)
     * @param level
     */
    public void setLevel(int level) {
        player.multiplier+=1;
        this.level = level;
    }
}
