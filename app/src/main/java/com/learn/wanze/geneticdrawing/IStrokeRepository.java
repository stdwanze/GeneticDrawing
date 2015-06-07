package com.learn.wanze.geneticdrawing;

import java.util.List;

/**
 * Created by Stefan Dienst on 07.06.2015.
 */
public interface IStrokeRepository {

    List<IPersistentStroke> retrieveStrokes();
}
