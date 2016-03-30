package com.example.lucas.fotagmobile;

public class ImageModel {
	private int m_resourceId;
	private int m_rating;
	private ImageCollectionModel m_model;

	public ImageModel(ImageCollectionModel model, int id) {
		m_resourceId = id;
		m_model = model;
		m_rating = 0;
	}

	public int getRating() {
		return m_rating;
	}

	public int getResourceId() {
		return m_resourceId;
	}

	public void setRating(int rating) {
		m_rating = rating;
		m_model.notifyViews();
	}
}
