package com.learn.wanze.geneticdrawing;

import android.graphics.Path;
import android.util.Log;
import android.view.animation.PathInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Dienst on 06.05.2015.
 */
public class SynchronizedModel {

    private Object sync = new Object();

    private List<Point> current =  new ArrayList<Point>();
    private StoredAccess access = null;
    public SynchronizedModel(StoredAccess access)
    {
        this.access = access;
    }
    public void newStroke()
    {
        synchronized (sync){
            access.store(this.current);
            this.current = new ArrayList<Point>();
        }

    }
    public List<Point> getDrawCopyCurrent(){
        synchronized (sync){
            List<Point> copy = new ArrayList<Point>();
            copy.addAll(this.current);
            return copy;
        }

    }
    public List<Point> getDrawCopyLastStroke(){
        synchronized (sync){
            List<Point> copy = new ArrayList<Point>();
            copy.addAll(this.access.getLastStroke());
            return copy;
        }

    }

    public void addPoint (Point p)
    {
        synchronized (sync) {

            this.current.add(p);
        }
    }
    public List<List<Point>> getOlds(){
        synchronized (sync) {
            Log.d("SynchronizedModel", "getOlds");
            List<List<Point>> olds = new ArrayList<List<Point>>();
            List<Point> old = this.access.first();
            while(old != null)
            {
                Log.d("SynchronizedModel", "addStroke");
                olds.add(old);
                old = this.access.getNext();
            }
            return olds;
        }
    }
}
