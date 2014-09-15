package com.example.activitydesigner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityInfoActivity extends Activity{

	ImageView back = null;//返回按钮
	TextView edit = null;
	TextView name = null;
	TextView date = null;
	TextView time = null;
	TextView location = null;
	TextView invented = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activityinfo);
		resInital();
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		name.setText("活动名字: "+(String)bundle.get("Ac_name"));
		time.setText("活动时间: "+(String)bundle.get("Ac_time"));
		date.setText("活动日期: "+(String)bundle.get("Ac_date"));
		location.setText("活动地点: "+(String)bundle.get("Ac_location"));
		invented.setText("活动邀请人: "+(String)bundle.get("Ac_invented"));
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Log.i("ActivityInfoActivity","back onClick invoke success!");
				ActivityInfoActivity.this.finish();
			}
		});
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Log.i("ActivityInfoActivity", "edit onClick invoke success!");
			}
		});
	}
	
	
	private void resInital()
	{
		back = (ImageView)findViewById(R.id.activityinfo_back);
		edit = (TextView)findViewById(R.id.activity_edit);
		name = (TextView)findViewById(R.id.activityInfo_name);
		location = (TextView)findViewById(R.id.activityInfo_location);
		date = (TextView)findViewById(R.id.activityInfo_date);
		time = (TextView)findViewById(R.id.activityInfo_time);
		invented = (TextView)findViewById(R.id.activityInfo_invented);
	}
}
