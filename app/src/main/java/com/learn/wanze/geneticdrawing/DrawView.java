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

public class DrawView extends View implements OnTouchListener, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener, IFlingFunc {
    private static final String TAG = "DrawView";

    List<Point> points = new ArrayList<Point>();
    Paint paintTemplate = new Paint();
    Paint paintCurrent = new Paint();
    Paint paintGeneration = new Paint();
    GestureDetector gestureDetector;
    SynchronizedModel access;
    DrawMode mode = DrawMode.Input;
    FlingHandler flingHandler = new FlingHandler();
    float maxHeight = 0;
    StrokeChooser chooser;
    public DrawView(Context context, StoredAccess access) {
        super(context);

        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paintTemplate.setColor(Color.BLACK);
        paintTemplate.setAntiAlias(true);
        paintTemplate.setStrokeWidth(5);

        paintCurrent.setColor(Color.BLUE);
        paintCurrent.setAntiAlias(true);
        paintCurrent.setStrokeWidth(20);

        paintGeneration.setColor(Color.rgb(0, 50, 50));
        paintGeneration.setAntiAlias(true);
        paintGeneration.setStrokeWidth(20);
        this.access = new SynchronizedModel(access);
        this.flingHandler.setHandler(this);

        StrokeRepo fake = new StrokeRepo();
        this.chooser = new StrokeChooser(fake,(IChooseResultHandler) context);
    }

    public void makeGestureEnabled(Activity act) {
        gestureDetector = new GestureDetector(act, this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    public void renew() {
        this.access.newStroke();
    }


    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawLine(0,  this.getHeight()/3*2, this.getWidth(), this.getHeight()/3*2, this.paintTemplate);

        if (mode == DrawMode.Input) {

            new Stroke(this.access.getDrawCopyCurrent()).draw(canvas, this.paintCurrent);
            new Stroke(this.access.getDrawCopyLastStroke()).draw(canvas, this.paintTemplate);

        } else {
            Log.d(TAG, "DrawOld mode");
            List<List<Point>> olds =  this.access.getOlds();
            int increment = 255 / olds.size();
            if(increment < 0) increment = 1;

            for (List<Point> stroke : this.access.getOlds()) {
                new Stroke(stroke).draw(canvas, getNextGenerationPaint(increment));
            }

        }
    }

    public Paint getNextGenerationPaint(int increment) {

        int color = paintGeneration.getColor();
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        paintGeneration.setColor(Color.rgb(red+increment,green,blue));

        return paintGeneration;
    }

    public boolean onTouch(View view, MotionEvent event) {
        // if(event.getAction() != MotionEvent.ACTION_DOWN)
        // return super.onTouchEvent(event);
        float height = view.getHeight();
        maxHeight = (height/3)*2;

        this.gestureDetector.onTouchEvent(event);

        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        if(point.y < maxHeight) {
            this.access.addPoint(point);
        }
        invalidate();
     //   Log.d(TAG, "point: " + point);

        //    return super.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {

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
        if(maxHeight < e1.getY()) {
            this.flingHandler.process(e1, e2, velocityX, velocityY);
        }
/*

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
        }


  */
        return false;
    }

    @Override
    public void up() {
        this.chooser.show();
    }

    @Override
    public void down() {

    }

    @Override
    public void left() {
        Log.d(TAG, "Right to Left swipe performed");
        this.mode = DrawMode.Input;
        invalidate();
    }

    @Override
    public void right() {
        Log.d(TAG, "Left to Right swipe performed");
        this.mode = DrawMode.ShowGenerations;
        invalidate();
    }
}

