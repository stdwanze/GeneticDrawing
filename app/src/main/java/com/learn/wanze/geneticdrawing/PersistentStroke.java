package com.learn.wanze.geneticdrawing;

/**
 * Created by Stefan Dienst on 07.06.2015.
 */
public class PersistentStroke implements IPersistentStroke {


    private String _name ;
    private int _id;

    public PersistentStroke(int id, String name)
    {
        this._id = id;
        this._name = name;
    }

    @Override
    public int getId() {
        return this._id;
    }

    @Override
    public String getName() {
        return this._name;
    }
}
