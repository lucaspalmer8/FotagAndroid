package com.example.lucas.fotagmobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.util.ArrayList;

public class ImageCollectionView extends LinearLayout implements ViewInterface {

	private static ImageCollectionModel MODEL = null;
	private ArrayList<MobileImageView> m_imageViews = new ArrayList<MobileImageView>();

	public static void setMODEL(ImageCollectionModel model) {
		MODEL = model;
	}

	public ImageCollectionView(Context context) {
		super(context);
		init(context);
	}

	public ImageCollectionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ImageCollectionView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context ctx) {
		LayoutInflater.from(ctx).inflate(R.layout.image_collection_view, this, true);
		MODEL.addObserver(this);
	}

	@Override
	public void notifyView() {
		TableLayout column1 = (TableLayout)findViewById(R.id.first_column);
		TableLayout column2 = (TableLayout)findViewById(R.id.second_column);

		for (MobileImageView view : m_imageViews) {
			view.notifyView();
		}
		if (m_imageViews.size() != MODEL.getVisibleImages()) {
			m_imageViews = new ArrayList<MobileImageView>();
			column1.removeAllViews();
			column2.removeAllViews();
			for(int i = 0; i < MODEL.getImages().size(); i++) {
				if (MODEL.getImages().get(i).getRating() < MODEL.getRatingFilter()) continue;
				m_imageViews.add(new MobileImageView(MODEL.getImages().get(i), getContext()));
				column1.addView(m_imageViews.get(m_imageViews.size() - 1));
				if (++i >= MODEL.getImages().size()) break;
				while (MODEL.getImages().get(i).getRating() < MODEL.getRatingFilter()) {
					i++;
					if (i >= MODEL.getImages().size()) break;
				}
				if (i >= MODEL.getImages().size()) break;
				m_imageViews.add(new MobileImageView(MODEL.getImages().get(i), getContext()));
				column2.addView(m_imageViews.get(m_imageViews.size() - 1));
			}
		}
	}
}