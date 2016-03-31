package com.example.lucas.fotagmobile;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.view.menu.ActionMenuItem;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private static String SAVED_STATE = "saved_state_array";
    private static String SAVED_RATING = "saved_rating";
    private ImageCollectionModel m_imageCollectionModel = null;
    private int[] m_ratingids = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5};
    private View m_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_imageCollectionModel = new ImageCollectionModel();

        if (savedInstanceState != null) {
            ArrayList<MobileImageModel> imageList =
                    (ArrayList<MobileImageModel>)savedInstanceState.getSerializable(SAVED_STATE);
            int rating = savedInstanceState.getInt(SAVED_RATING);
            m_imageCollectionModel.setImageList(imageList);
            m_imageCollectionModel.setRatingFilter(rating);
        }

        //Create views/controller and tell it about model.
        ImageCollectionView.setMODEL(m_imageCollectionModel);
        ImageCollectionListView.setMODEL(m_imageCollectionModel);
        setContentView(R.layout.layout);

        //Notify the views of the model.
        m_imageCollectionModel.notifyViews();

        //Set up menu bar.
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        m_menu = mInflater.inflate(R.layout.custon_action_bar, null);
        TextView titleTextView = (TextView) m_menu.findViewById(R.id.title_text);
        titleTextView.setText("Fotag Mobile");

        setUpRatingBar();

        ImageView view;
        for (int i = 0; i < m_imageCollectionModel.getRatingFilter(); i++) {
            view = (ImageView) m_menu.findViewById(m_ratingids[i]);
            final int k = 1;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_imageCollectionModel.setRatingFilter(k + 1);
                    setUpRatingBar();
                }
            });
        }
        for (int i = m_imageCollectionModel.getRatingFilter(); i < m_ratingids.length; i++) {
            view = (ImageView) m_menu.findViewById(m_ratingids[i]);
            final int k = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_imageCollectionModel.setRatingFilter(k + 1);
                    setUpRatingBar();
                }
            });
        }
        view = (ImageView) m_menu.findViewById(R.id.resetFilter);
        view.setImageBitmap(MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.whitereset, 100, 100));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_imageCollectionModel.setRatingFilter(0);
                setUpRatingBar();
            }
        });
        view = (ImageView) m_menu.findViewById(R.id.loadButton);
        view.setImageBitmap(MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.loadicon, 100, 100));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        view = (ImageView) m_menu.findViewById(R.id.resetButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mActionBar.setCustomView(m_menu);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE, m_imageCollectionModel.getImages());
        outState.putInt(SAVED_RATING, m_imageCollectionModel.getRatingFilter());
    }

    public void setUpRatingBar() {
        ImageView view;
        for (int i = 0; i < m_imageCollectionModel.getRatingFilter(); i++) {
            view = (ImageView) m_menu.findViewById(m_ratingids[i]);
            view.setImageBitmap(MobileImageView.STAR);
        }
        for (int i = m_imageCollectionModel.getRatingFilter(); i < m_ratingids.length; i++) {
            view = (ImageView) m_menu.findViewById(m_ratingids[i]);
            view.setImageBitmap(MobileImageView.EMPTY);
        }
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
