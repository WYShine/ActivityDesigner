package com.example.activitydesigner;

import java.util.ArrayList;

import resource.CircleItemPagerAdapter;
import resource.ImageMeta;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class CircleImageItemActivity extends FragmentActivity{
	
	public static final String CURRENT_ITEM_TAG = "ActivityDesigner_CURRENT_ITEM";
	public static final String IMAGE_META_LIST_TAG = "ActivityDesigner_IMAGE_META_LIST_TAG";
	private ArrayList<ImageMeta> imageList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("CircleImageItemActivity", "onCreate invoke success!");
		setContentView(R.layout.activity_circle_image_item);
		int currentIndex = getIntent().getIntExtra(CURRENT_ITEM_TAG, 0);
		imageList = getIntent().getParcelableArrayListExtra(IMAGE_META_LIST_TAG);
		ViewPager viewPager = (ViewPager) findViewById(R.id.circle_item_viewPager);
		PagerAdapter adapter = new CircleItemPagerAdapter
				(getSupportFragmentManager(),imageList);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentIndex);
	}
}
