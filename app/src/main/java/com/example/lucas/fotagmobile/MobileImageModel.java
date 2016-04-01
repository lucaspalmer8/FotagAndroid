package com.example.lucas.fotagmobile;

import android.app.Activity;
import android.graphics.Bitmap;

import java.io.Serializable;

public class MobileImageModel implements Serializable {
	private int m_resourceId;
	private int m_rating;
	private String m_uri;
	transient private ImageCollectionModel m_model;

	public MobileImageModel(ImageCollectionModel model, int id) {
		m_resourceId = id;
		m_model = model;
		m_rating = 0;
		m_uri = null;
	}

	//For adding a uri image.
	public MobileImageModel(ImageCollectionModel model, String uri) {
		m_resourceId = -1;
		m_model = model;
		m_rating = 0;
		m_uri = uri;
	}

	public String getUri() {
		return m_uri;
	}

	//For use on restoring state.
	public void setModel(ImageCollectionModel model) {
		m_model = model;
	}

	public MainActivity getMainActivity() {
		return m_model.getMainActivity();
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
