package com.example.lucas.fotagmobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

public class ImageCollectionListView extends LinearLayout implements ViewInterface {

    private static ImageCollectionModel MODEL = null;
    private ArrayList<MobileImageView> m_imageViews = new ArrayList<MobileImageView>();

    public static void setMODEL(ImageCollectionModel model) {
        MODEL = model;
    }

    public ImageCollectionListView(Context context) {
        super(context);
        init(context);
    }

    public ImageCollectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageCollectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        LayoutInflater.from(ctx).inflate(R.layout.image_collection_list_view, this, true);
        // extra init
        MODEL.addObserver(this);
    }

    @Override
    public void notifyView() {
        for (MobileImageView view : m_imageViews) {
            view.notifyView();
        }

        TableLayout column = (TableLayout)findViewById(R.id.column);
        //TableLayout c2 = (TableLayout)findViewById(R.id.second_column);
        if (m_imageViews.size() != MODEL.getVisibleImages()) {
            m_imageViews = new ArrayList<MobileImageView>();
            column.removeAllViews();
            //c2.removeAllViews();
            for(int i = 0; i < MODEL.getImages().size(); i++) {
                if (MODEL.getImages().get(i).getRating() < MODEL.getRatingFilter()) continue;
                m_imageViews.add(new MobileImageView(MODEL.getImages().get(i), getContext()));
                column.addView(m_imageViews.get(m_imageViews.size() - 1));
            }
        }
    }
}

