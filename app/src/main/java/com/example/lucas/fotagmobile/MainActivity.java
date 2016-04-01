package com.example.lucas.fotagmobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    //For saving and restoring state.
    private static String SAVED_STATE = "saved_state_array";
    private static String SAVED_RATING = "saved_rating";

    //To save memory, reuse the same images.
    private static Bitmap STAR = null;
    private static Bitmap EMPTY = null;

    //Model that stores all the image data.
    private ImageCollectionModel m_imageCollectionModel;

    //Ids of every rating bar star.
    private int[] m_ratingIds = {R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5};

    //Menu bar reference to make changes.
    private View m_menu;

    //Bitmap member for decoding checking if provided uri is valid.
    private Bitmap m_bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (STAR == null) {
            STAR = MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.fullstar, 50, 50);
        }
        if (EMPTY == null) {
            EMPTY = MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.emptystar, 50, 50);
        }

        m_imageCollectionModel = new ImageCollectionModel(this);

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
            view = (ImageView) m_menu.findViewById(m_ratingIds[i]);
            final int k = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m_imageCollectionModel.setRatingFilter(k + 1);
                    setUpRatingBar();
                }
            });
        }
        for (int i = m_imageCollectionModel.getRatingFilter(); i < m_ratingIds.length; i++) {
            view = (ImageView) m_menu.findViewById(m_ratingIds[i]);
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
        view = (ImageView) m_menu.findViewById(R.id.uriButton);
        view.setImageBitmap(MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.web, 100, 100));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText txtUrl = new EditText(v.getContext());

                //txtUrl.setHint("http://www.librarising.com/astrology/celebs/images2/QR/queenelizabethii.jpg");
                final View v2 = v;
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Image Uri Selection")
                        .setMessage("Paste in the link of an image!")
                        .setView(txtUrl)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String uri = txtUrl.getText().toString();
                                addUri(uri, v2);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
            }
        });
        view = (ImageView) m_menu.findViewById(R.id.loadButton);
        view.setImageBitmap(MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.loadicon, 100, 100));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_imageCollectionModel.loadImages();
            }
        });
        view = (ImageView) m_menu.findViewById(R.id.resetButton);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_imageCollectionModel.resetImages();
            }
        });

        mActionBar.setCustomView(m_menu);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void showImage(int id, String uri, Bitmap bitmap) {
        Intent i = new Intent(getApplicationContext(), ImageDialog.class);
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            i.putExtra(ImageDialog.IMAGE_BITMAP, byteArray);
        }
        i.putExtra(ImageDialog.IMAGE_ID, id);
        i.putExtra(ImageDialog.IMAGE_URI, uri);
        startActivity(i);
    }

    public void addUri(String uri, View v) {
        final String uri1 = uri;
        final View v2 = v;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                m_bitmap = null;
                try {
                    InputStream in = new URL(uri1).openStream();
                    m_bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                if (m_bitmap == null) {
                    new AlertDialog.Builder(v2.getContext())
                            .setTitle("Error")
                            .setMessage("Not a valid url!")
                            .setPositiveButton("Ok", null)
                            .show();
                } else {
                    m_imageCollectionModel.addImage(new MobileImageModel(m_imageCollectionModel, uri1));
                }
            }
        }.execute();
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
            view = (ImageView) m_menu.findViewById(m_ratingIds[i]);
            view.setImageBitmap(STAR);
        }
        for (int i = m_imageCollectionModel.getRatingFilter(); i < m_ratingIds.length; i++) {
            view = (ImageView) m_menu.findViewById(m_ratingIds[i]);
            view.setImageBitmap(EMPTY);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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