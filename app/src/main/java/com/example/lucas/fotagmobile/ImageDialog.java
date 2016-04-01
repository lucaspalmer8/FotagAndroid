package com.example.lucas.fotagmobile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageDialog extends Activity {

    public static String IMAGE_ID = "image_id_intent";
    public static String IMAGE_URI = "image_uri_intent";
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_dialog);

        Bundle extras = getIntent().getExtras();
        int value = extras.getInt(IMAGE_ID);
        final String uri = extras.getString(IMAGE_URI);

        final ImageView mDialog = (ImageView)findViewById(R.id.my_image);

        if (uri != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        InputStream in = new URL(uri).openStream();
                        bmp = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        // log error
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    if (bmp != null)
                        mDialog.setImageBitmap(bmp);
                }

            }.execute();

        } else {
            mDialog.setImageBitmap(
                    MainActivity.decodeSampledBitmapFromResource(getResources(), value, 200, 200));
        }

        //mDialog.setImageBitmap(MainActivity.decodeSampledBitmapFromResource(getResources(), value, 200, 200));
        mDialog.setClickable(true);


        //finish the activity (dismiss the image dialog) if the user clicks
        //anywhere on the image
        mDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
