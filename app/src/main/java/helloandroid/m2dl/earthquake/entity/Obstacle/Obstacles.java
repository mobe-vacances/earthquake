package helloandroid.m2dl.earthquake.entity.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import helloandroid.m2dl.earthquake.MainActivity;
import helloandroid.m2dl.earthquake.entity.player.Player;

public class Obstacles {
    private static final int MIN_POSITION = 200; //emplacement de la ligne de démarcation
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
            p.x = (getPositionRandom(MainActivity.sharedPref.getInt("screen_width", 200)));
            p.y = (getPositionRandom(MainActivity.sharedPref.getInt("screen_height", 200)));
            notValid = touchWithMarge(p);
        }
        return p;
    }

    private boolean touchWithMarge(Crack point){
        for(Crack p : cracks){
            if(( Math.abs(p.x - point.x) < WIDTH_DEVIL + MARGING) && (Math.abs(p.y - point.y) < HEIGHT_DEVIL + MARGING)){
                return true;
            }
        }
        return false;
    }

    public boolean touch(Player player){
        for(Crack p : cracks){
            if(p.isDanger() && ( Math.abs(p.x - player.getPosition().x) < WIDTH_DEVIL) && (Math.abs(p.y - player.getPosition().y) < HEIGHT_DEVIL)){
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
            if((cracks.size() == 0 )||(cracks.get(cracks.size() - 1).isInoffensive())) {
                add(getLocationValid());
            }
        }
        if(canAddNewObstacle()){
            deleteFirst();
            add(getLocationValid());
        }
    }
}
