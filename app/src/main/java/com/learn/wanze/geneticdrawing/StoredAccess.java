package com.learn.wanze.geneticdrawing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Stefan Dienst on 03.05.2015.
 */
public class StoredAccess {



    private List<Point> base = new ArrayList<Point>();
    private List<List<Point>> stack = new ArrayList<List<Point>>();

    private List<List<Point>> generationsOutPut = new ArrayList<List<Point>>();
    public StoredAccess()
    {
        initBase();
    }

    public List<Point> first()
    {
        generationsOutPut.addAll(stack);
        return generationsOutPut.remove(0);
    }
    public List<Point> getNext(){
        List<Point> ret = null;
        if(generationsOutPut.size() > 0)
        {
            ret = generationsOutPut.remove(0);
        }
        return ret;
    }

    public List<Point> getLastStroke(){
        return stack.get(stack.size()-1);
    }
    public void store(List<Point> stroke){
        stack.add(stroke);
    };

    private void initBase(){

        float x = 400;
        float y = 50;

        for(int start = 0; start < 800; start++)
        {
            base.add( new Point(x,y + start) );
        }
        stack.add(base);
    }


}
