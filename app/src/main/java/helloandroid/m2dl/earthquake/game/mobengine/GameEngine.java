package helloandroid.m2dl.earthquake.game.mobengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helloandroid.m2dl.earthquake.R;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Drawable;
import helloandroid.m2dl.earthquake.game.mobengine.elements.Updatable;
import helloandroid.m2dl.earthquake.game.mobengine.statics.SoundStore;


public class GameEngine {

    private static boolean running;

    private static final List<Drawable> drawables = new ArrayList<>();

    private static final List<Updatable> updatables = new ArrayList<>();

    private static final List<Object> elementsToRemove = new ArrayList<>();

    private static final List<Object> elementsToAdd = new ArrayList<>();

    public static boolean isRunning() {
        return running;
    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public List<Updatable> getUpdatables() {
        return updatables;
    }

    public static void addGameElements(Object... objects) {
        elementsToAdd.addAll(Arrays.asList(objects));
    }

    public static void removeGameElement(Object object) {
        elementsToRemove.add(object);
    }

    public static void start() {
        running = true;
        SoundStore.playSound(R.raw.rip_droit_auteur,100);
        SoundStore.startAllPaused();
    }

    public static void pause() {
        running = false;
        SoundStore.pauseAll();
    }

    public static void reset() {
        drawables.clear();
        updatables.clear();
    }

    public void removeElementsToRemove() {
        for (Object object : elementsToRemove) {
            if(object instanceof Drawable) {
                drawables.remove(object);
            }
            if(object instanceof Updatable) {
                updatables.remove(object);
            }
        }
        elementsToRemove.clear();
    }

    public void addElementsToAdd() {
        for (Object object : elementsToAdd) {
            if(object instanceof Drawable) {
                drawables.add((Drawable) object);
            }
            if(object instanceof Updatable) {
                updatables.add((Updatable) object);
            }
        }
        drawables.sort((d1, d2) -> d1.getZIndex() - d2.getZIndex());
        elementsToAdd.clear();
    }
}
