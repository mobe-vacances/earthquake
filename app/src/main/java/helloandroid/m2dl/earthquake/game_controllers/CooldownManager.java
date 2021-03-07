package helloandroid.m2dl.earthquake.game_controllers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;

import helloandroid.m2dl.earthquake.MainActivity;
import helloandroid.m2dl.earthquake.entity.player.Player;

public class CooldownManager {
    private boolean bulletTimeDispo;
    private int cooldownBulletTimeProgress =200;
    private Player player;
    private int defaultCooldownValue;
    private Handler handlerBulletTime;

    public CooldownManager(Player player,int defaultCooldownValue){
        bulletTimeDispo = true;
        this.player=player;
        this.defaultCooldownValue = defaultCooldownValue;
    }


    public void drawBulletTimeIndicator(Canvas canvas, WindowManager wm){
        //indicateur bullet time

        int width = MainActivity.sharedPref.getInt("screen_width",300);
        int height = MainActivity.sharedPref.getInt("screen_height",300);

        Paint indicateurBulletTime = new Paint();
        Paint indicateurBulletTimeContour = new Paint();
        //va falloir trouver un meilleur endroit
        RectF positionIndicateur = new RectF( width-400,
                30,
                width-200 +25,
                70
        );

        //contenu

        indicateurBulletTime.setStyle(Paint.Style.FILL);
        indicateurBulletTime.setColor(setBulletTimeIndicateurColor());
        //contour

        indicateurBulletTimeContour.setStyle(Paint.Style.STROKE);
        indicateurBulletTimeContour.setColor(Color.BLACK);
        indicateurBulletTimeContour.setStrokeWidth(10);
        //draw

        if(bulletTimeDispo) {
            canvas.drawRect(positionIndicateur, indicateurBulletTime);
        }
        else {
            updateCooldown(canvas,width,height);
        }
        canvas.drawRect(positionIndicateur, indicateurBulletTimeContour);
    }


    private void updateCooldown(Canvas canvas, int width, int height) {
        Paint cooldown = new Paint();
        cooldown.setStyle(Paint.Style.FILL);
        cooldown.setColor(Color.GREEN);
        int right = width - 200 - cooldownBulletTimeProgress;
        RectF positionIndicateur = new RectF( width-400,
                30,
                right,
                70
        );
        canvas.drawRect(positionIndicateur, cooldown);
        //vitesse du cooldown (visuel)
        //-19
        cooldownBulletTimeProgress -= 1 ;

    }



    public boolean isBulletTimeDispo() {
        return bulletTimeDispo;
    }

    public void setBulletTimeDispo(boolean bulletTimeDispo) {
        this.bulletTimeDispo = bulletTimeDispo;
    }


    private int setBulletTimeIndicateurColor(){
        if(bulletTimeDispo){
            return Color.GREEN;
        }
        else{
            return Color.RED;
        }
    }

    public void activateBulletTime(int speed, int lenght) {
        player.setStep(player.getStep()/2);
        handlerBulletTime = new Handler();
        handlerBulletTime.postDelayed(setUpdateValueToTenWithTimeout,5000);
    }

    private Runnable setUpdateValueToTenWithTimeout = new Runnable() {
        @Override
        public void run() {
            player.setStep(40);
            bulletTimeDispo = true;
            cooldownBulletTimeProgress = defaultCooldownValue;
        }
    };

}
