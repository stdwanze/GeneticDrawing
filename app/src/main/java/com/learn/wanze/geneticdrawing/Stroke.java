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

    public Stroke(List<Point> storedPoints)
    {
        tAG += "stored";
        this.set(storedPoints);
    }




    public void draw(Canvas canvas, Paint paint)
    {

        Point last =  null;
        for (Point point : this.points) {

            if(last != null){
                canvas.drawLine(last.x, last.y,point.x,point.y, paint);
            }
            last = point;

         //   Log.d(this.tAG, "Painting: " + point);
        }
    }


    public void set(List<Point> lastStroke) {
        this.points = lastStroke;
    }
}
