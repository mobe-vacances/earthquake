package helloandroid.m2dl.earthquake.game_controllers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

import helloandroid.m2dl.earthquake.R;

public class BitmapRepository {

    private Map<Integer,Bitmap> map = new HashMap<>();

    public BitmapRepository(Resources resources) {
        map.put(R.drawable.crack, BitmapFactory.decodeResource(resources, R.drawable.coronavirus_safe));
        map.put(R.drawable.crack_danger, BitmapFactory.decodeResource(resources, R.drawable.coronavirus));
        map.put(R.drawable.smile, BitmapFactory.decodeResource(resources, R.drawable.smile));
        map.put(R.drawable.hero, BitmapFactory.decodeResource(resources, R.drawable.world));
        map.put(R.drawable.mask, BitmapFactory.decodeResource(resources, R.drawable.mask));
        map.put(R.drawable.desinfectant, BitmapFactory.decodeResource(resources, R.drawable.desinfectant));
    }

    public Bitmap getBitmap(int id) {
        return map.get(id);
    }
}
