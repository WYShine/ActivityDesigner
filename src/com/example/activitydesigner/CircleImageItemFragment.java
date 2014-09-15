package com.example.activitydesigner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CircleImageItemFragment extends Fragment{

	 private ImageView imageView;
	 private TextView locationView;
	 private TextView textView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_circle_image_item,container,false);
		imageView = (ImageView) view.findViewById(R.id.image_item_image);
		locationView = (TextView) view.findViewById(R.id.image_item_location);
	    locationView.setTextColor(Color.BLUE);
	    locationView.setText("a");
	    textView = (TextView) view.findViewById(R.id.image_item_text);
	    textView.setText("x");
	    Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getBaseContext().getResources(), R.drawable.demo_4);
		imageView.setImageBitmap(bitmap);
		return view;
	}
}
