package com.learn.wanze.geneticdrawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Dienst on 03.05.2015.
 */
public class Stroke {
    private String tAG = "Template";
    private List<Point> points = new ArrayList<Point>();
    private static final int SIZE = 20;
    public Stroke(String name)
    {
        tAG += name;
    }
    public Stroke(List<Point> storedPoints)
    {
        tAG += "stored";
        this.set(storedPoints);
    }


    public void add(Point p)
    {
        points.add(p);
    }

    public void draw(Canvas canvas, Paint paint)
    {
        for (Point point : this.points) {
            canvas.drawCircle(point.x, point.y, SIZE, paint);
            Log.d(this.tAG, "Painting: " + point);
        }
    }

    public List<Point> release(){
        List<Point> ret = this.points;
        this.points = new ArrayList<Point>();

        return ret;
    }
    public void removePoints(int count)
    {
        for(int i = 0; i < count ; i++) {
            this.points.remove(this.points.size() - 1);
        }
    }
    public void set(List<Point> lastStroke) {
        this.points = lastStroke;
    }
}
