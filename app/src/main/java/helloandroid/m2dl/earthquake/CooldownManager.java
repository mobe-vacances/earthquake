package helloandroid.m2dl.earthquake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.view.Display;
import android.view.WindowManager;

public class CooldownManager {
    private boolean bulletTimeDispo;
    private int cooldownBulletTimeProgress =200;

    public CooldownManager(){
        bulletTimeDispo = true;
    }


    public void drawBulletTimeIndicator(Canvas canvas, WindowManager wm){
        //indicateur bullet time

        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Paint indicateurBulletTime = new Paint();
        Paint indicateurBulletTimeContour = new Paint();
        //va falloir trouver un meilleur endroit
        RectF positionIndicateur = new RectF( width-400,
                30,
                width-200 +25,
                170
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
                170
        );
        canvas.drawRect(positionIndicateur, cooldown);
        //vitesse du cooldown (visuel)
        //-19
        cooldownBulletTimeProgress -= 3 ;
        System.out.println(cooldownBulletTimeProgress);

    }



    public boolean isBulletTimeDispo() {
        return bulletTimeDispo;
    }

    public void setBulletTimeDispo(boolean bulletTimeDispo) {
        this.bulletTimeDispo = bulletTimeDispo;
    }


    public int getCooldownBulletTimeProgress() {
        return cooldownBulletTimeProgress;
    }

    public void setCooldownBulletTimeProgress(int cooldownBulletTimeProgress) {
        this.cooldownBulletTimeProgress = cooldownBulletTimeProgress;
    }

    private int setBulletTimeIndicateurColor(){
        if(bulletTimeDispo){
            return Color.GREEN;
        }
        else{
            return Color.RED;
        }
    }
}
