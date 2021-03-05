package helloandroid.m2dl.earthquake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacles {
  private static final int MIN_POSITION = 10 ;
    private static final int MARGING  = 150;
    private static final int WIDTH_DEVIL  = 100;
    private static final int HEIGHT_DEVIL  = 100;
    private static final int MAX_DEVIL  = 5;

    public List<Crack> cracks;

    public List<Crack> getCracks(){
        return cracks;
    }
    public Obstacles(){
        cracks = new ArrayList<>();
    }

    private void add(Crack p){
        cracks.add(p);
    }

    private Crack getLocationValid() {
        boolean notValid = true;
        Crack p = new Crack();
        while(notValid){
            p.setX(getPositionRandom(MainActivity.sharedPref.getInt("screen_width", 200)));
            p.setY(getPositionRandom(MainActivity.sharedPref.getInt("screen_height", 200)));
            notValid = touchWithMarge(p);
        }
        return p;
    }

    private boolean touchWithMarge(Point point){
        for(Point p : cracks){
            if(( Math.abs(p.getX() - point.getX()) < WIDTH_DEVIL + MARGING) && (Math.abs(p.getY() - point.getY()) < HEIGHT_DEVIL + MARGING)){
                return true;
            }
        }
        return false;
    }

    private int getPositionRandom(int max){
        Random rand = new Random();
       return rand.nextInt(max - WIDTH_DEVIL - MIN_POSITION*2 + 1) + MIN_POSITION;
    }

    private void deleteFirst() {
        cracks.remove(0);
    }

    private boolean canAddNewObstacle() {
        for(Crack c : cracks){
            if(c.isEnding()){
                return true;
            }
        }
        return false;
    }

    public void evolObstacle() {
        for(Crack c : cracks){
            c.addRoundLife();
        }
        if(cracks.size() < MAX_DEVIL){
            add(getLocationValid());
        }
        if(canAddNewObstacle()){
            deleteFirst();
            add(getLocationValid());
        }
    }
}
