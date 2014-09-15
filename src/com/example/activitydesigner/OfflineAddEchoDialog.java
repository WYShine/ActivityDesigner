package com.example.activitydesigner;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OfflineAddEchoDialog extends Dialog{

	ListView echo_listview;
	ArrayList<HashMap<String, String>> echo_list = null; 
	
	public OfflineAddEchoDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public OfflineAddEchoDialog(Context context,int style)
	{
		super(context,style);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_offline_add_echo);
		echo_listview = (ListView)findViewById(R.id.offline_add_echo_listview);
		getDataSource1();
		setAdapter();
	}
	
	/**
	 * echo_listview 数据初始化
	 * @return
	 */
	private ArrayList<HashMap<String, String>> getDataSource1() {

		echo_list = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();
		HashMap<String, String> map5 = new HashMap<String, String>();
		HashMap<String, String> map6 = new HashMap<String, String>();
		map1.put("item", "无");
		map2.put("item", "每天");
		map3.put("item", "每周");
		map4.put("item", "每两周");
		map5.put("item", "每月");
		map6.put("item", "每年");
		echo_list.add(map1);
		echo_list.add(map2);
		echo_list.add(map3);
		echo_list.add(map4);
		echo_list.add(map5);
		echo_list.add(map6);
		return echo_list;
	}
	
	/**
	 * Listview adapeter 设置
	 */
	private void setAdapter()
	{
		SimpleAdapter adapter1 = new SimpleAdapter(
				getContext(),
				echo_list,R.layout.simple_list_item_1, 
				new String[] { "item" }, new int[] { R.id.item_title });
		    echo_listview.setAdapter(adapter1);
	}
}
