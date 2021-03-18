package helloandroid.m2dl.earthquake.game.mobengine.statics;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SoundStore {

    private static Map<Integer, MediaPlayer> mediaPlayerMap;

    private static Map<MediaPlayer, Integer> pausedMediaPlayers;

    private static boolean animationActive;

    public static void setAnimationActive(boolean act){
        animationActive = act;
    }

    public static boolean getAnimationActive(){
        return animationActive;
    }

    public static void createMediaPlayers(int[] soundIds, Context context) {
        pausedMediaPlayers = new HashMap<>();
        mediaPlayerMap = new HashMap<>();
        for(Integer soundId : soundIds) {
            mediaPlayerMap.put(soundId, MediaPlayer.create(context, soundId));
        }
    }

    public static void releaseMediaPlayers() {
        for(MediaPlayer mp : mediaPlayerMap.values()) {
            mp.release();
        }
        mediaPlayerMap.clear();
    }

    public static void playSound(int soundId, float volume) {
        if(!animationActive){
            return;
        }
        mediaPlayerMap.get(soundId).setVolume(volume, volume);
        mediaPlayerMap.get(soundId).seekTo(0);
        mediaPlayerMap.get(soundId).start();
    }

    public static void loopSound(int soundId, float volume) {
        if(!animationActive){
            return;
        }
        mediaPlayerMap.get(soundId).setLooping(true);
        mediaPlayerMap.get(soundId).setVolume(volume, volume);
        mediaPlayerMap.get(soundId).seekTo(0);
        mediaPlayerMap.get(soundId).start();
    }

    public static void stopLoopedSound(int soundId) {
        mediaPlayerMap.get(soundId).setLooping(false);
        mediaPlayerMap.get(soundId).stop();
    }

    public static void pauseAll() {
        for(MediaPlayer mp : mediaPlayerMap.values()) {
            if(mp.isPlaying()) {
                mp.pause();
                pausedMediaPlayers.put(mp,mp.getCurrentPosition());
            }
        }
    }

    public static void startAllPaused() {
        Set setOfKey = pausedMediaPlayers.keySet();
        Iterator i = setOfKey.iterator();
        while (i.hasNext()){
            MediaPlayer mp = (MediaPlayer) i.next();
            Integer truc = pausedMediaPlayers.get(mp);
            mp.seekTo(truc);
            mp.start();
        }

        /*
        for(MediaPlayer mp : pausedMediaPlayers) {
            mp.
            mp.start();
        }
        */

        pausedMediaPlayers.clear();
    }
}
