package com.example.lucas.fotagmobile;

import java.util.ArrayList;

public class ImageCollectionModel {

	private ArrayList<ViewInterface> m_observers = new ArrayList<ViewInterface>();
	private ArrayList<ImageModel> m_images = new ArrayList<ImageModel>();
	private int m_ratingFilter = 0;

	public ImageCollectionModel() {
		for (int i = 0; i < 2; i++) {
			m_images.add(new ImageModel(this, R.drawable.dolphin));
			m_images.add(new ImageModel(this, R.drawable.duck));
			m_images.add(new ImageModel(this, R.drawable.piglet));
			m_images.add(new ImageModel(this, R.drawable.elephant));
		}
	}

	public void setRatingFilter(int filter) {
		m_ratingFilter = filter;
		notifyViews();
	}

	public int getRatingFilter() {
		return m_ratingFilter;
	}

	public int getVisibleImages() {
		int counter = 0;
		for (ImageModel model : m_images) {
			if (model.getRating() >= m_ratingFilter) {
				counter++;
			}
		}
		return counter;
	}

	public void addObserver(ViewInterface view) {
		m_observers.add(view);
		notifyViews();
	}

	public void notifyViews() {
		for (ViewInterface view : m_observers) {
			view.notifyView();
		}
	}

	public ArrayList<ImageModel> getImages() {
		return m_images;
	}
}
