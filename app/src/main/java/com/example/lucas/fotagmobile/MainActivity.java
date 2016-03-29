package com.example.lucas.fotagmobile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        ImageCollectionModel model = new ImageCollectionModel();

        //Create view/controller and tell it about model.
        ImageCollectionView view = new ImageCollectionView(model, getApplicationContext());
        model.addObserver(view);

        //Notify the views of the model.
        model.notifyViews();


    }
}
