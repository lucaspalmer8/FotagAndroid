package com.example.lucas.fotagmobile;

import java.util.ArrayList;

public class ImageCollectionModel {

	private ArrayList<ViewInterface> m_observers = new ArrayList<ViewInterface>();
	private ArrayList<MobileImageModel> m_images = new ArrayList<MobileImageModel>();
	private int m_ratingFilter = 0;

	public ImageCollectionModel() {
		for (int i = 0; i < 4; i++) {
			//m_images.add(new MobileImageModel(this, R.drawable.dolphin));
			m_images.add(new MobileImageModel(this, R.drawable.duck));
			m_images.add(new MobileImageModel(this, R.drawable.piglet));
			//m_images.add(new MobileImageModel(this, R.drawable.elephant));
		}
	}

	public void setImageList(ArrayList<MobileImageModel> images) {
		m_images = images;
		for (MobileImageModel model : m_images) {
			model.setModel(this);
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
		for (MobileImageModel model : m_images) {
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

	public ArrayList<MobileImageModel> getImages() {
		return m_images;
	}
}
