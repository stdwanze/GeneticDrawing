package com.learn.wanze.geneticdrawing;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Stefan Dienst on 07.06.2015.
 */

public class FlingHandler {

    private IFlingFunc client;

    public void setHandler(IFlingFunc func)
    {
        this.client = func;
    }


    public void process(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        float diffY = e1.getY() - e2.getY(); //negative on down
        float diffX = e1.getX() - e2.getX(); // negative on right;
        Log.d("FLING", "diffX:"+diffX +" diffY:"+diffY);
        // horizontal movement
        if(Math.abs(diffX) > Math.abs(diffY))
        {
            if (e1.getX() < e2.getX()) {
                Log.d("FLING","right");
                this.client.right();
            }
            else {
                Log.d("FLING","left");
                this.client.left();
            }
        }
        else // vertical movement
        {
            if (e1.getY() > e2.getY()) {
                Log.d("FLING","up");
                this.client.up();
            }
            else {
                Log.d("FLING","down");
                this.client.down();
            }
        }

    }
}
