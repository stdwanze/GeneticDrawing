package com.learn.wanze.geneticdrawing;

import android.app.Activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
    DrawView drawView;
    StoredAccess access;
    private GestureDetector gestureDetector;
  /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Set full screen view
     //   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
     //           WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //
    //    requestWindowFeature(Window.FEATURE_NO_TITLE);
        access = new StoredAccess();
        drawView = new DrawView(this,access);
        drawView.makeGestureEnabled(this);


        setContentView(drawView);
        drawView.requestFocus();
    }


}
