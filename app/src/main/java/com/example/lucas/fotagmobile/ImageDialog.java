package com.example.lucas.fotagmobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageDialog extends Activity {

    public static String IMAGE_ID = "image_id_intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_dialog);

        Bundle extras = getIntent().getExtras();
        int value = extras.getInt(IMAGE_ID);


        ImageView mDialog = (ImageView)findViewById(R.id.my_image);
        mDialog.setImageBitmap(MainActivity.decodeSampledBitmapFromResource(getResources(), value, 200, 200));
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
