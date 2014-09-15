package com.example.activitydesigner;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class OfflineAddTimeDialog extends Dialog implements android.view.View.OnClickListener{

	ListView time_listView;
	TextView sure;
	TextView back;
	TimePicker timePicker;
	public int Hour;
	public int Minute;
	ArrayList<HashMap<String, String>> time_list = null; 
	public OfflineAddTimeDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public OfflineAddTimeDialog(Context context,int style)
	{
		super(context,style);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_offline_add_time);
		Log.i("OfflineAddTimeDialog", "onCreate invoke success!");
		time_listView = (ListView)findViewById(R.id.offline_add_time_listview);
		timePicker = (TimePicker)findViewById(R.id.offline_add_time_picker);
		sure = (TextView)findViewById(R.id.offline_add_time_sure);
		back = (TextView)findViewById(R.id.offline_add_time_back);
		getDataSource1();
		setAdapter();
		sure.setOnClickListener(this);
		back.setOnClickListener(this);
		timePicker.setEnabled(true);
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				// TODO Auto-generated method stub
				Log.i("OfflineAddTimeDialog", "onTimeChanged invoke success!");
				Hour = hour;
				Minute = minute;
				
			}
		});
	}
	
	/**
	 * echo_listview 数据初始化
	 * @return
	 */
	private ArrayList<HashMap<String, String>> getDataSource1() {

		time_list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		map1.put("item", "开始");
		map2.put("item", "结束");
		map3.put("item", "");
		time_list.add(map1);
		time_list.add(map2);
		time_list.add(map3);
		return time_list;
	}
	
	/**
	 * Listview adapeter 设置
	 */
	private void setAdapter()
	{
		SimpleAdapter adapter1 = new SimpleAdapter(
				getContext(),
				time_list,R.layout.simple_list_item_1, 
				new String[] { "item" }, new int[] { R.id.item_title });
		time_listView.setAdapter(adapter1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.offline_add_time_back:
			Log.i("","");
			break;
		case R.id.offline_add_time_sure:
			Log.i("","");
			break;
		default:
			break;
		}
	}
}
