package com.example.activitydesigner;



import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MainLeftFragment extends Fragment implements OnClickListener{

	private View circleLayout;
	private View settingLayout;
	private View managementLayout;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_main_left, container,false);
		circleLayout = view.findViewById(R.id.circleLayout);
		settingLayout = view.findViewById(R.id.settingLayout);
		managementLayout = view.findViewById(R.id.managementLayout);
		circleLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		managementLayout.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		Fragment newFragment = null;
		switch (v.getId()) {
		case R.id.circleLayout:
			 //newFragment = new CircleFragment();
			Intent intent = new Intent(getActivity().getBaseContext(),CircleActivity.class);
			startActivity(intent);
			 circleLayout.setSelected(true);
			 settingLayout.setSelected(false);
			 managementLayout.setSelected(false);
			break;
		case R.id.settingLayout:
			 newFragment = new SettingFragment();
			 circleLayout.setSelected(false);
			 settingLayout.setSelected(true);
			 managementLayout.setSelected(false);		
			break;
		case R.id.managementLayout:
			 newFragment = new MainFragment();
			 circleLayout.setSelected(false);
			 managementLayout.setSelected(true);
			 settingLayout.setSelected(false);
		default:
			break;
		}
		if(newFragment != null)
		switchFragment(newFragment);

	}
	
	private void switchFragment(Fragment fragment)
	{
		if(getActivity() == null)
			return;
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.switchContent(fragment);
	}
}
