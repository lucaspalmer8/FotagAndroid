package com.example.lucas.fotagmobile;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MobileImageView extends LinearLayout implements ViewInterface {

	protected ImageModel m_model;
	protected RatingBar m_ratingBar;

	public MobileImageView(ImageModel model, Context context) {
		super(context);
		m_model = model;
		m_ratingBar = new RatingBar(m_model.getRating(), getContext());
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
			setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					m_model.setRating(m_index);
					notifyView();
					return true;
				}
			});
		}
	}

	private class EmptyStar extends ImageView {
		private int m_index;

		public EmptyStar(int index, Context context) {
			super(context);
			m_index = index;
			setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					m_model.setRating(m_index);
					notifyView();
					return true;
				}
			});
		}
	}

	private class RatingBar extends LinearLayout {
		private int m_rating;
	
		public RatingBar(int rating, Context context) {
			super(context);
			m_rating = rating;
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
