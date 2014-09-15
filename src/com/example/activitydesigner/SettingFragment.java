package com.example.activitydesigner;



import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	   // TODO Auto-generated method stub
	  View view = inflater.inflate(R.layout.fragment_setting,container,false);
	  Log.i("SettingFragment", "onCreateView 调用成功");
	  return view;
	}
}
