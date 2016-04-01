package com.example.lucas.fotagmobile;

import android.app.Activity;

import java.util.ArrayList;

public class ImageCollectionModel {

	private ArrayList<ViewInterface> m_observers = new ArrayList<ViewInterface>();
	private ArrayList<MobileImageModel> m_images = new ArrayList<MobileImageModel>();
	private int m_ratingFilter = 0;
	private MainActivity m_mainActivity = null;

	public ImageCollectionModel(MainActivity activity) {
		m_mainActivity = activity;
	}

	public MainActivity getMainActivity() {
		return m_mainActivity;
	}

	public void loadImages() {
		m_images = new ArrayList<MobileImageModel>();
		m_images.add(new MobileImageModel(this, R.drawable.dolphin));
		m_images.add(new MobileImageModel(this, R.drawable.duck));
		m_images.add(new MobileImageModel(this, R.drawable.piglet));
		m_images.add(new MobileImageModel(this, R.drawable.elephant));
		m_images.add(new MobileImageModel(this, R.drawable.fish));
		m_images.add(new MobileImageModel(this, R.drawable.camelian));
		m_images.add(new MobileImageModel(this, R.drawable.camel));
		m_images.add(new MobileImageModel(this, R.drawable.weasel));
		m_images.add(new MobileImageModel(this, R.drawable.goose));
		m_images.add(new MobileImageModel(this, R.drawable.cobra));
		notifyViews();
	}

	public void resetImages() {
		m_images = new ArrayList<MobileImageModel>();
		notifyViews();
	}

	public void addImage(MobileImageModel model) {
		m_images.add(model);
		notifyViews();
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
