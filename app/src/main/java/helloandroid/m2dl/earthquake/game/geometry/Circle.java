package helloandroid.m2dl.earthquake.game.geometry;

import android.graphics.Point;
import android.graphics.Rect;

public class Circle {

    private final Rect enclosingRect;

    public Circle(Rect enclosingRect) {
        this.enclosingRect = enclosingRect;
    }

    // https://gamedev.stackexchange.com/questions/7172/how-do-i-find-the-intersections-between-colliding-circles
    public static boolean intersects(Circle c1, Circle c2) {
        return Math.sqrt(
                Math.pow(
                        c1.getEnclosingRect().exactCenterX() - c2.getEnclosingRect().exactCenterX(),
                        2
                ) + Math.pow(
                        c1.getEnclosingRect().exactCenterY() - c2.getEnclosingRect().exactCenterY(),
                        2
                )
        ) <= (c1.getEnclosingRect().width() + c2.getEnclosingRect().width() ) / 2.0;
    }

    public Rect getEnclosingRect() {
        return enclosingRect;
    }


    // https://www.geeksforgeeks.org/check-if-any-point-overlaps-the-given-circle-and-rectangle/#:~:text=Case%201%3A%20The%20side%20of,that%20both%20the%20shapes%20intersect.
    public static boolean intersects(Circle c, Rect r) {
        Point nearestFromCircle = new Point();
        nearestFromCircle.x = (int) Math.max(r.left, Math.min(c.getEnclosingRect().exactCenterX(), r.right));
        nearestFromCircle.y = (int) Math.max(r.top, Math.min(c.getEnclosingRect().exactCenterY(), r.bottom));

        return Math.pow(c.getEnclosingRect().exactCenterX() - nearestFromCircle.x, 2) +
                Math.pow(c.getEnclosingRect().exactCenterY() - nearestFromCircle.y , 2)
                <= Math.pow(c.getEnclosingRect().width() / 2.0 ,2);
    }
}
