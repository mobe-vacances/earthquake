package helloandroid.m2dl.earthquake.entity.Obstacle;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import helloandroid.m2dl.earthquake.MainActivity;
import helloandroid.m2dl.earthquake.entity.player.Player;
import helloandroid.m2dl.earthquake.game.GameView;

public class Obstacles {
    private static final int MARGIN = 10;
    public static final int WIDTH_ENNEMY = 50;
    public static final int HEIGHT_ENNEMY = 50;

    /**
     * Définit les emplacements disponibles pour le positionnement d'un obstacle.
     */
    private final Obstacle[][] availableSlots;
    private final int nbLines;
    private final int nbColumns;
    private final int nbMaxOccurences;

    public List<Obstacle> obstacles;

    public List<Obstacle> getObstacles(){
        return obstacles;
    }


    public Obstacles(int nbElements){
        nbMaxOccurences = nbElements;
        nbLines = MainActivity.sharedPref.getInt("screen_width", 200) / (WIDTH_ENNEMY + MARGIN);
        int hauteurUtile = MainActivity.sharedPref.getInt("screen_height", 200) - GameView.HEIGHT_BARRE;
        nbColumns = hauteurUtile / (HEIGHT_ENNEMY + MARGIN);
        availableSlots = new Obstacle[nbLines][nbColumns];
        obstacles = new ArrayList<>();
    }

    private Point getCoordinates(int i, int j) {
        return new Point(i * WIDTH_ENNEMY, j * HEIGHT_ENNEMY + GameView.HEIGHT_BARRE + MARGIN);
    }

    private boolean isAvailable(Obstacle o) {
        return o == null;
    }

    /**
     * random de 0 à n
     */
    private int randomInt(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }

    /**
     * Random de -n à n
     */
    private int randomRelativeInt(int range) {
        return randomInt(range * 2) - range;
    }

    private void addNewObstacle() {
        Obstacle obs = new Obstacle();
        int randomLine, randomColumn;
        do {
            randomLine = randomInt(nbLines);
            randomColumn = randomInt(nbColumns);
        } while(!isAvailable(availableSlots[randomLine][randomColumn]));
        Point res = getCoordinates(randomLine, randomColumn);
        obs.x = res.x + randomRelativeInt(MARGIN);
        obs.y = res.y + randomRelativeInt(MARGIN);
        availableSlots[randomLine][randomColumn] = obs;
        obstacles.add(obs);
    }

    public boolean touch(Player player,boolean dangerous){
        for(Obstacle p : obstacles){
            if(dangerous && p.isDanger() && ( Math.abs(p.x - player.getPosition().x) < WIDTH_ENNEMY) && (Math.abs(p.y - player.getPosition().y) < HEIGHT_ENNEMY)){
                return true;
            }
            if(!dangerous && ( Math.abs(p.x - player.getPosition().x) < WIDTH_ENNEMY) && (Math.abs(p.y - player.getPosition().y) < HEIGHT_ENNEMY)){
                p.setState(StateObstacle.ENDING);
                return true;
            }
        }
        return false;
    }

    private void deleteAllObstacleEnding() {
        for(int line = 0; line < nbLines; line++) {
            for(int column = 0; column < nbColumns; column++) {
                Obstacle o = availableSlots[line][column];
                if(!isAvailable(o) && o.isEnding()) {
                    obstacles.remove(o);
                    availableSlots[line][column] = null;
                }
            }
        }
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
        if(obstacles.size() < nbMaxOccurences){
            if((obstacles.size() == 0 )||(obstacles.get(obstacles.size() - 1).isInoffensive())) {
                addNewObstacle();
            }
        }

        if(canAddNewObstacle()){
            deleteAllObstacleEnding();
            addNewObstacle();
        }
    }
}
