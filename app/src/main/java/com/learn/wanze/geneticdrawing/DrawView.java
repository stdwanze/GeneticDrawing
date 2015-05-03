package com.learn.wanze.geneticdrawing;

/**
 * Created by Stefan Dienst on 03.05.2015.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View implements OnTouchListener, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {
    private static final String TAG = "DrawView";

    List<Point> points = new ArrayList<Point>();
    Paint paintTemplate = new Paint();
    Paint paintCurrent = new Paint();
    Paint paintGeneration = new Paint();
    Stroke template = new Stroke("base");
    Stroke current = new Stroke("base");
    GestureDetector gestureDetector;
    StoredAccess access;
    DrawMode mode = DrawMode.Input;
    int nonDrawInput = 0;
    public DrawView(Context context, StoredAccess access) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paintTemplate.setColor(Color.BLACK);
        paintTemplate.setAntiAlias(true);

        paintCurrent.setColor(Color.BLUE);
        paintCurrent.setAntiAlias(true);

        paintGeneration.setColor(Color.GRAY);
        paintGeneration.setAntiAlias(true);
        paintGeneration.setAlpha(20);
        this.access = access;
        template.set(access.getLastStroke());
    }

    public void makeGestureEnabled(Activity act) {
        gestureDetector = new GestureDetector(act, this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    public void renew() {
        this.access.store(this.current.release());
        this.template.set(access.getLastStroke());
    }


    @Override
    public void onDraw(Canvas canvas) {
        if(mode == DrawMode.Input) {
            this.template.draw(canvas, this.paintTemplate);
            this.current.draw(canvas, this.paintCurrent);
        }
        else
        {
            List<Point> points = this.access.first();
            while(points != null)
            {
                new Stroke(points).draw(canvas,getNextGenerationPaint());

                points = this.access.getNext();
            }
        }
    }
    public Paint getNextGenerationPaint(){
        paintGeneration.setAlpha((int)(paintGeneration.getAlpha()* 1.1));
        return paintGeneration;
    }
    public boolean onTouch(View view, MotionEvent event) {
        // if(event.getAction() != MotionEvent.ACTION_DOWN)
        // return super.onTouchEvent(event);
        this.gestureDetector.onTouchEvent(event);
        if(nonDrawInput <=0) {


            Point point = new Point();
            point.x = event.getX();
            point.y = event.getY();
            current.add(point);
            invalidate();
            Log.d(TAG, "point: " + point);
        }
        else { nonDrawInput-- ;}
        //    return super.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
       this.current.removePoints(5);
        nonDrawInput = 5;
        this.renew();
        Log.d(TAG, "onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        nonDrawInput = 10;
        if (e1.getX() < e2.getX()) {
            Log.d(TAG, "Left to Right swipe performed");
            this.mode = DrawMode.ShowGenerations;
            invalidate();
        }

        if (e1.getX() > e2.getX()) {
            Log.d(TAG, "Right to Left swipe performed");
            this.mode = DrawMode.Input;
            invalidate();
        }


        return false;
    }
}

