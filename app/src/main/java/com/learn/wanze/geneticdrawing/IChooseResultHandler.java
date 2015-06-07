package com.learn.wanze.geneticdrawing;

import android.app.Activity;

/**
 * Created by Stefan Dienst on 07.06.2015.
 */
public interface IChooseResultHandler {

    Activity getActivity();
    void onChoose(String name, int id);
}
