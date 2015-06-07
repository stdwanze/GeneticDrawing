package com.learn.wanze.geneticdrawing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stefan Dienst on 07.06.2015.
 */
public class StrokeRepo implements IStrokeRepository {

    private List<IPersistentStroke> strokes = new ArrayList<IPersistentStroke>();


    public StrokeRepo(){

        for(int i =0; i < 10; i++)
        {
            strokes.add(new PersistentStroke(i,"Name"+i));
        }

    }
    @Override
    public List<IPersistentStroke> retrieveStrokes() {
        return strokes;
    }
}
