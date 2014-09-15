package com.example.activitydesigner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class FirstActivity extends Activity{

	private TextView register ;
	private TextView login;
	private TextView offline;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		resInitial();
		
		register.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Log.i("FirstActivity", "register onTouch 调用成功");
				Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
				startActivity(intent);
				return false;
			}
			
			});
		
		 login.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				Log.i("FirstActivity", "login onTouch 调用成功");
				Intent intent = new Intent(getBaseContext(),LoginActivity.class);
				startActivity(intent);
				return false;
			}
		});
		 
		 offline.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("FirstActivity", "offline onClick invoke success!");
				Intent intent = new Intent(getBaseContext(),OfflineActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * 当前界面控件初始化
	 */
	private void resInitial()
	{
		register = (TextView)findViewById(R.id.first_register);
		login = (TextView)findViewById(R.id.first_login);
		offline = (TextView)findViewById(R.id.first_offline);
	}
}

