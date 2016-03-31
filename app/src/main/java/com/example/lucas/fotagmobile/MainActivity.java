package com.example.lucas.fotagmobile;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private static String SAVED_STATE = "saved_state_array";
    private ImageCollectionModel m_imageCollectionModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_imageCollectionModel = new ImageCollectionModel();

        if (savedInstanceState != null) {
            ArrayList<MobileImageModel> imageList =
                    (ArrayList<MobileImageModel>)savedInstanceState.getSerializable(SAVED_STATE);
            m_imageCollectionModel.setImageList(imageList);
        }

        //Create view/controller and tell it about model.
        ImageCollectionView.setMODEL(m_imageCollectionModel);
        ImageCollectionListView.setMODEL(m_imageCollectionModel);
        setContentView(R.layout.layout);
        //ImageCollectionModel.ADD_OBSERVER();
        //ImageCollectionView view = new ImageCollectionView(model, getApplicationContext());
        //model.addObserver(view);



        //Notify the views of the model.
        m_imageCollectionModel.notifyViews();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE, m_imageCollectionModel.getImages());
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
