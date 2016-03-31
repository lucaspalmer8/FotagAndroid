package com.example.lucas.fotagmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MobileImageView extends LinearLayout implements ViewInterface {

	private MobileImageModel m_model;
	private RatingBar m_ratingBar;
	private static Bitmap STAR = null;
	private static Bitmap EMPTY = null;

	public MobileImageView(MobileImageModel model, Context context) {
		super(context);

		if (STAR == null) {
			STAR = MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.fullstar, 100, 100);
		}
		if (EMPTY == null) {
			EMPTY = MainActivity.decodeSampledBitmapFromResource(getResources(), R.drawable.emptystar, 100, 100);
		}
		m_model = model;
		m_ratingBar = new RatingBar(m_model.getRating(), getContext());

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		setLayoutParams(lp);
		setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundColor(Color.WHITE);
		setPadding(20, 50, 20, 50);

		LinearLayout.LayoutParams imagelp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
		ImageView image = new ImageView(getContext());
		image.setLayoutParams(imagelp);
		image.setImageBitmap(
				MainActivity.decodeSampledBitmapFromResource(getResources(), m_model.getResourceId(), 100, 100));
		addView(image);
		addView(m_ratingBar);
	}

	public int getRating() {
		return m_model.getRating();
	}
	
	@Override
	public void notifyView() {
		int rating = m_model.getRating();
		m_ratingBar.updateRatingBar(rating);
	}

	private class FullStar extends ImageView {
		private int m_index;

		public FullStar(int index, Context context) {
			super(context);
			m_index = index;
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
			setLayoutParams(lp);
			setImageBitmap(STAR);
			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					m_model.setRating(m_index);
					notifyView();
				}
			});
		}
	}

	private class EmptyStar extends ImageView {
		private int m_index;

		public EmptyStar(int index, Context context) {
			super(context);
			m_index = index;
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
			setLayoutParams(lp);
			setImageBitmap(EMPTY);
			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					m_model.setRating(m_index);
					notifyView();
				}
			});
		}
	}

	private class RatingBar extends LinearLayout {
		private int m_rating;
	
		public RatingBar(int rating, Context context) {
			super(context);
			m_rating = rating;
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			setLayoutParams(lp);
			setOrientation(LinearLayout.HORIZONTAL);
			setBackgroundColor(Color.WHITE);
			for (int i = 1; i <= rating; i++) {
				addView(new FullStar(i, getContext()));
			}
			for (int i = rating + 1; i <= 5; i++) {
				addView(new EmptyStar(i, getContext()));
			}
		}
		
		public void updateRatingBar(int rating) {
			if (rating == m_rating) {
				return;
			}
			m_rating = rating;
			removeAllViews();
			for (int i = 1; i <= rating; i++) {
				addView(new FullStar(i, getContext()));
			}
			for (int i = rating + 1; i <= 5; i++) {
				addView(new EmptyStar(i, getContext()));
			}
		}
	}
}
