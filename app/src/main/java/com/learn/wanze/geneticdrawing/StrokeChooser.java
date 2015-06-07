package com.learn.wanze.geneticdrawing;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.ArrayMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stefan Dienst on 07.06.2015.
 */
public class StrokeChooser {

    private IStrokeRepository _repo;
    private IChooseResultHandler _reciever;
    private Map<String,Integer> _persistentStrokes = new HashMap<String,Integer>();
    private Dialog _dialog;
    private String[] _items ;
    public StrokeChooser(IStrokeRepository repo, IChooseResultHandler reciever)
    {
        _repo = repo;
        _reciever = reciever;
    }


    private void init(){
        load();

        AlertDialog.Builder builder = new AlertDialog.Builder(_reciever.getActivity());
        builder.setTitle("Pick old Run")
                .setItems(_items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = _items[which];
                        int id = _persistentStrokes.get(name);
                        _reciever.onChoose(name, id);
                    }
                });
        _dialog =  builder.create();
    }
    private void load(){
        List<IPersistentStroke> strokes = _repo.retrieveStrokes();
        _items = new String[strokes.size()];
        int i = 0;
        for(IPersistentStroke stroke: strokes){
            _items[i++] = stroke.getName();
            _persistentStrokes.put(stroke.getName(), stroke.getId());
        };
    }
    public void show(){

         init();
         _dialog.show();

    }



}
