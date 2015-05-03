package com.learn.wanze.geneticdrawing;

/**
 * Created by Stefan Dienst on 03.05.2015.
 */
public class Point {
    float x, y;

        public Point()
        {

        }
        public Point(float x, float y)
        {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return x + ", " + y;
        }
}

