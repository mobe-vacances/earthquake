package helloandroid.m2dl.earthquake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Devils {
  private static final int MIN_POSITION = 10 ;
    private static final int MARGING  = 150;
    private static final int WIDTH_DEVIL  = 100;
    private static final int HEIGHT_DEVIL  = 100;

    public List<Point> devils;

    public List<Point> getDevils(){
        return devils;
    }
    public Devils(){
        devils = new ArrayList<>();
    }

    public void add(Point p){
        devils.add(p);
    }

    public Point getLocationValid() {
        boolean notValid = true;
        Point p = new Point();
        while(notValid){
            p.setX(getPositionRandom(MainActivity.sharedPref.getInt("screen_width", 200)));
            p.setY(getPositionRandom(MainActivity.sharedPref.getInt("screen_height", 200)));
            notValid = touchWithMarge(p);
        }
        return p;
    }

    public boolean touchWithMarge(Point point){
        for(Point p : devils){
            if(( Math.abs(p.getX() - point.getX()) < WIDTH_DEVIL + MARGING) && (Math.abs(p.getY() - point.getY()) < HEIGHT_DEVIL + MARGING)){
                return true;
            }
        }
        return false;
    }

    public int getPositionRandom(int max){
        Random rand = new Random();
       return rand.nextInt(max - WIDTH_DEVIL - MIN_POSITION*2 + 1) + MIN_POSITION;
    }
}
