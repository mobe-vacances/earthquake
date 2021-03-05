package helloandroid.m2dl.earthquake;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

public class BitmapRepository {

    private Map<Integer,Bitmap> map = new HashMap<>();

    public BitmapRepository(Resources resources) {
        map.put(R.drawable.crack, BitmapFactory.decodeResource(resources, R.drawable.crack));
        map.put(R.drawable.crack_danger, BitmapFactory.decodeResource(resources, R.drawable.crack_danger));
        map.put(R.drawable.smile, BitmapFactory.decodeResource(resources, R.drawable.smile));
    }

    public Bitmap getBitmap(int id) {
        return map.get(id);
    }
}
