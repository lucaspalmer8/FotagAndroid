package com.example.lucas.fotagmobile;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ImageCollectionView extends LinearLayout implements ViewInterface {

	private ImageCollectionModel m_model;
	private ArrayList<MobileImageView> m_imageViews = new ArrayList<MobileImageView>();

	public ImageCollectionView(ImageCollectionModel model, Context context) {
		super(context);
		m_model = model;
	}

	@Override
	public void notifyView() {
		for (MobileImageView view : m_imageViews) {
			view.notifyView();
		}
		if (m_imageViews.size() != m_model.getImages().size()) {
			m_imageViews = new ArrayList<MobileImageView>();
			for(int i = 0; i < m_model.getImages().size(); i++) {
				m_imageViews.add(new MobileImageView(m_model.getImages().get(i), getContext()));
			}
		}
	}
}
