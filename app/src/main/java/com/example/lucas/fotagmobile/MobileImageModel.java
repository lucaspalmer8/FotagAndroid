package com.example.lucas.fotagmobile;

import java.io.Serializable;

public class MobileImageModel implements Serializable {
	private int m_resourceId;
	private int m_rating;
	transient private ImageCollectionModel m_model;

	public MobileImageModel(ImageCollectionModel model, int id) {
		m_resourceId = id;
		m_model = model;
		m_rating = 0;
	}

	//For use on restoring state.
	public void setModel(ImageCollectionModel model) {
		m_model = model;
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
