package com.example.activitydesigner;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class Register_1Fragment extends Fragment{

	EditText user = null;
	EditText pass = null;
	Button sure = null;
	Button back = null;
	String userTemp;
	String passTemp;
	Fragment newFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.fragment_register_1, container,false);
		user = (EditText)view.findViewById(R.id.register1_user);
		pass = (EditText)view.findViewById(R.id.register1_pass);
		sure = (Button)view.findViewById(R.id.register1_sure);
		back = (Button)view.findViewById(R.id.register1_back);
		
		back.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				//返回登录界面
				getActivity().finish();
				return false;
			}
		});
		
		sure.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userTemp = user.getText().toString();
				passTemp = pass.getText().toString();
				//如果成功
				resource.MyResource.regUser = userTemp;
				resource.MyResource.regPass = passTemp;
				newFragment = new Register_2Fragment();
				switchFragment(newFragment);
				
				
			}
			
		});
		return view;	
	}
		
	
	
	/**
	 * 判断输入是否合法
	 * @return
	 */
	private boolean judge(String user,String pass)
	{
		if(user=="" || user==null)
		{
			Log.i("RegisterActivity", "用户名为空");
			return false ;
		}
		if(pass=="" || pass==null)
		{
			Log.i("RegisterActivity", "密码为空");
			return false;
		}
		Log.i("RegisterActivity","注册成功，账号为： "+user+"密码为： "+pass);
		return true;
	}
	
	private void switchFragment(Fragment fragment)
	{
		if(getActivity() == null)
			return;
		RegisterActivity registerActivity = (RegisterActivity)getActivity();
		registerActivity.switchContent(fragment);
	}
	
	
	
	/**
	 * 验证注册是否成功
	 * @return
	 */
	private boolean Validation()
	{
		return false;
	}
	
	class RegisterThread extends Thread
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		}
	}
}
