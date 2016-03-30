package com.example.lucas.fotagmobile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
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
		// extra init
		MODEL.addObserver(this);
	}

	@Override
	public void notifyView() {
		TableLayout column1 = (TableLayout)findViewById(R.id.first_column);
		TableLayout column2 = (TableLayout)findViewById(R.id.second_column);
		column1.removeAllViews();
		column2.removeAllViews();

		for (int i = 0; i < MODEL.getImages().size(); i++) {
			ImageView image = new ImageView(getContext());
			image.setImageBitmap(
					MainActivity.decodeSampledBitmapFromResource(getResources(), MODEL.getImages().get(i).getResourceId(), 100, 100));
			//image.setImageDrawable(getResources().getDrawable(model.getImages().get(i).getResourceId()));
			column1.addView(image);
			//column2.addView(new ImageView(getApplicationContext()));
			//break;
			if (++i >= MODEL.getImages().size()) break;
			image = new ImageView(getContext());
			image.setImageBitmap(
					MainActivity.decodeSampledBitmapFromResource(getResources(), MODEL.getImages().get(i).getResourceId(), 100, 100));
			column2.addView(image);
			//break;
		}
		/*for (MobileImageView view : m_imageViews) {
			view.notifyView();
		}
		if (m_imageViews.size() != m_model.getImages().size()) {
			m_imageViews = new ArrayList<MobileImageView>();
			for(int i = 0; i < m_model.getImages().size(); i++) {
				m_imageViews.add(new MobileImageView(m_model.getImages().get(i), getContext()));
			}
		}*/
	}
}
