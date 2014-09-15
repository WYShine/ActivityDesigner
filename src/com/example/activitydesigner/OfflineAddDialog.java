package com.example.activitydesigner;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.mapapi.map.Symbol.Color;

import junit.framework.Test;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class OfflineAddDialog extends Dialog implements OnClickListener{

	private TextView sure;
	private TextView cancle;
	private View time;
	private View echo;
	private View parentCurrent;
	private EditText title;
	private EditText location;
	private EditText notes;
	Dialog dialog;
	
	OfflineAddDialog(Context context)
	{
		super(context);
		setOwnerActivity((Activity) context);
	}
	
	OfflineAddDialog(Context context,int res)
	{
		super(context,res);
		setOwnerActivity((Activity) context);
	}
	public OfflineAddDialog(Context context,int res,View parentCurrent) 
	{
		// TODO Auto-generated constructor stub
		super(context,res);
		this.parentCurrent = parentCurrent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_offline_add);
		resInitial();
		sure.setOnClickListener(this);
		cancle.setOnClickListener(this);
		time.setOnClickListener(this);
		echo.setOnClickListener(this);
		
	}

	private void resInitial()
	{
		sure = (TextView)findViewById(R.id.offline_add_sure);
		cancle = (TextView)findViewById(R.id.offline_add_cancle);
		time = (View)findViewById(R.id.offline_add_time);
		title = (EditText)findViewById(R.id.offline_add_title);
		location = (EditText)findViewById(R.id.offline_add_location);
		echo = (View)findViewById(R.id.offline_add_echo);
		notes = (EditText)findViewById(R.id.offline_add_notes);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.offline_add_sure:
			Log.i("OfflineAddDialog", "sure onClick invoke success!");
			//完成了的话
			ImageView clock = (ImageView)parentCurrent.findViewById(R.id.calendar_day_cell_clock);
			clock.setBackgroundResource(R.drawable.ico_naozhong);
			this.dismiss();
			break;
		case R.id.offline_add_cancle:
			Log.i("OfflineAddDialog", "cancle onClick invoke success!");
			this.dismiss();
			break;
		case R.id.offline_add_time:
			Log.i("OfflineAddDialog", "start_end onClick invoke success!");
			//跳到选择时间的位置
			dialog = new OfflineAddTimeDialog(getContext(),R.style.dialog);
			dialog.show();
			break;
		case R.id.offline_add_echo:
			Log.i("OfflineAddDialog", "echo onClick invoke success!");
			//跳到选择重复的位置
			dialog = new OfflineAddEchoDialog(getContext(),R.style.dialog);
			dialog.show();
			break;
		default:
			break;
		}
	}
}
