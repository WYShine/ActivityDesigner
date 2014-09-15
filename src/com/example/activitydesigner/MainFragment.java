package com.example.activitydesigner;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainFragment extends Fragment{

	private MainActivity mainActivity;
	ImageView profile = null;
	ImageView add_center = null; //界面中间的添加按钮
	ImageView add_right = null;  //界面右边上角的添加按钮
	ImageButton present = null;
	ImageButton back = null;
	ImageButton msgInfo = null;
	ImageButton around = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	// TODO Auto-generated method stub
		Log.i("MainFragment", "onCreateView invoke success!");
		View view = inflater.inflate(R.layout.fragment_main, container,false);
		mainActivity = (MainActivity)getActivity();
		profile = (ImageView)view.findViewById(R.id.main_profile);
		add_right = (ImageView)view.findViewById(R.id.main_add_right);
		add_center = (ImageView)view.findViewById(R.id.main_add_center);
		present = (ImageButton)view.findViewById(R.id.main_present);
		back = (ImageButton)view.findViewById(R.id.main_back);
		msgInfo = (ImageButton)view.findViewById(R.id.main_msg);
		around = (ImageButton)view.findViewById(R.id.main_around);
		
		
		profile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Log.i("MainFragment", "profile onClick invoke success！");
				profile.setSelected(true);
				MainActivity mainActivity = (MainActivity)getActivity();
				mainActivity.toggle();
			}
		});
		
		add_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("MainFragment", "add_right onClick invoke success！");
				profile.setSelected(true);
				Intent intent = new Intent(mainActivity.getBaseContext(),
						OfflineActivity.class);
				startActivity(intent);
			}
		});
		
		add_center.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainFragment", "add onClick invoke success!");
				add_center.setSelected(true);
				Intent intent = new Intent(mainActivity.getBaseContext()
						,AddActivity.class);
				startActivity(intent);
			}
		});
		
		present.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainFragment", "present onClick invoke success!");
				present.setSelected(true);
				Intent intent = new Intent(mainActivity.getBaseContext()
						,PresentActivity.class);
				startActivity(intent);
			}
		});
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainFragment", "back onClick invoke success!");
				back.setSelected(true);
				Intent intent = new Intent(mainActivity.getBaseContext()
						,BackActivity.class);
				startActivity(intent);
			}
		});
		
		msgInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainFragment", "msgInfo onClick invoke success!");
				msgInfo.setSelected(true);
				//Intent intent = new Intent(mainActivity.getBaseContext()
						//,MsgInfoActivity.class);
				//startActivity(intent);
			}
		});
		
		around.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MainFragment", "around onClick invoke success!");
				around.setSelected(true);
				Intent intent = new Intent(mainActivity.getBaseContext()
						,AroundActivity.class);
				startActivity(intent);
			}
		});
		
		return view;	
  }
}
