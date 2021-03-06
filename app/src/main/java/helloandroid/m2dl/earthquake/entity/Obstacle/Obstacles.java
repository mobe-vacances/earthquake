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

    public List<Obstacle> obstacles;

    public List<Obstacle> getObstacles(){
        return obstacles;
    }
    public Obstacles(){
        obstacles = new ArrayList<>();
    }

    private void add(Obstacle p){
        obstacles.add(p);
    }

    private Obstacle getLocationValid() {
        boolean notValid = true;
        Obstacle p = new Obstacle();
        while(notValid){
            p.x = (getPositionRandom(MainActivity.sharedPref.getInt("screen_width", 200)));
            p.y = (getPositionRandom(MainActivity.sharedPref.getInt("screen_height", 200)));
            notValid = touchWithMarge(p);
        }
        return p;
    }

    private boolean touchWithMarge(Obstacle point){
        for(Obstacle p : obstacles){
            if(( Math.abs(p.x - point.x) < WIDTH_DEVIL + MARGING) && (Math.abs(p.y - point.y) < HEIGHT_DEVIL + MARGING)){
                return true;
            }
        }
        return false;
    }

    public boolean touch(Player player,boolean dangerous){
        for(Obstacle p : obstacles){
            if(p.isDanger() && ( Math.abs(p.x - player.getPosition().x) < WIDTH_DEVIL) && (Math.abs(p.y - player.getPosition().y) < HEIGHT_DEVIL)){
                return true;
            }
            if(!dangerous && ( Math.abs(p.x - player.getPosition().x) < WIDTH_DEVIL) && (Math.abs(p.y - player.getPosition().y) < HEIGHT_DEVIL)){
                p.setState(StateObstacle.ENDING);
                return true;
            }
        }
        return false;
    }

    private int getPositionRandom(int max){
        Random rand = new Random();
       return rand.nextInt(max - WIDTH_DEVIL - MIN_POSITION*2 + 1) + MIN_POSITION;
    }

    private void deleteAllObstacleEnding() {
        int index;
        while ((index = indexOfFirstObstacleEnding()) != -1){
            obstacles.remove(index);
        }
    }

    private int indexOfFirstObstacleEnding(){
        int index = 0;
        for(Obstacle o : obstacles){
            if(o.isEnding()){
                return index;
            }
            index++;
        }
        return -1;
    }

    private boolean canAddNewObstacle() {
        for(Obstacle c : obstacles){
            if(c.isEnding()){
                return true;
            }
        }
        return false;
    }

    public void evolObstacle() {
        for(Obstacle c : obstacles){
            c.addRoundLife();
        }
        if(obstacles.size() < MAX_DEVIL){
            if((obstacles.size() == 0 )||(obstacles.get(obstacles.size() - 1).isInoffensive())) {
                add(getLocationValid());
            }
        }

        if(canAddNewObstacle()){
            deleteAllObstacleEnding();
            add(getLocationValid());
        }
    }
}
