package com.example.activitydesigner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import resource.ConUtils;

import com.example.activitydesigner.LoginActivity.LoginThread;

import MyHttp.MyHttp;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OfflineLoginDialog extends Dialog implements OnClickListener{

	Context parentContext; 
	private TextView register;
	private EditText user;
	private EditText pass;
	private TextView sure;
	private TextView back;
	boolean success = false;//判断是否登陆成功
	boolean end = false; //判断登陆验证是否结束
	String username;//用户名
	String password;//密码
	Login login;
	private ProgressDialog mProgress;	//发送中。。。
	
	public OfflineLoginDialog(Context context) {
		super(context);
		this.parentContext = context;
		// TODO Auto-generated constructor stub
	}
	public OfflineLoginDialog(Context context,int style)
	{
		super(context, style);
		this.parentContext = context;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.dialog_offline_login);
    	Log.i("OfflineLoginDialog", "onCreate invoke success!");
    	resInitial();
    	register.setOnClickListener(this);
    	sure.setOnClickListener(this);
    	back.setOnClickListener(this);
    	login = new Login();
    }
    
    
    private void resInitial()
    {
    	register = (TextView)findViewById(R.id.offline_login_register);
    	user = (EditText)findViewById(R.id.offline_login_username);
    	pass = (EditText)findViewById(R.id.offline_login_password);
    	sure = (TextView)findViewById(R.id.offline_login_sure);
    	back = (TextView)findViewById(R.id.offline_login_back); 
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.offline_login_register:
			//跳转到注册的界面
			this.dismiss();
			Intent intent = new Intent(parentContext,RegisterActivity.class);
			parentContext.startActivity(intent);
			break;
		case R.id.offline_login_sure:
			//进行登录的验证
			Log.i("LoginActivity", "sure onClick 调用成功");
			username = user.getText().toString();
			password = pass.getText().toString();
			int judgeResult = judge(username, password);
			//对账号密码进行输入判断
			if (judgeResult == 0) 
			{
				//此时用户名或密码为空
				Toast.makeText(parentContext, "用户名或密码不能为空",Toast.LENGTH_SHORT).show();
			}
			if (judgeResult == 1) 
			{
				//此时用户名或密码长度过短
				Toast.makeText(parentContext, "用户名或密码长度过短",Toast.LENGTH_SHORT).show();
			}
			if (judgeResult == 2) 
			{
				//此时用户名或密码中有空格
				Toast.makeText(parentContext, "用户名或密码中有空格",Toast.LENGTH_SHORT).show();
			}
			if (judgeResult == 3) 
			{
				Bundle tmp = new Bundle();
				tmp.putString("Log_username", username);
				tmp.putString("Log_password", password);
				mProgress = ProgressDialog.show(parentContext,"" , "正在登陆");
			    login.execute(tmp);
			    this.dismiss();
			} 
			break;
		case R.id.offline_login_back:
			this.dismiss();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 判断输入是否合法
	 * @return
	 * 0: 表示用户名或密码为空
	 * 1: 表示用户名或密码长度过短
	 * 2： 表示用户名或密码中有空格
	 * 3: 表示验证成功
	 */
	private int judge(String user,String pass)
	{
		//如果输入为空
		if (user.isEmpty()||pass.isEmpty()) 
		{
			Log.i("OfflineLoginDialog", "judge 用户名或密码为空");
			return 0;
		}
		else 
		{
			int judge = 0; 
			char[] temp = user.toCharArray();
			for (int i = 0; i < temp.length; i++) 
			{
				if (temp[i] == ' ') 
				{
					//说明此时用户名中有空格
					judge = 1;
					break;
				}
			}
			//如果用户名中没空格 那么我们来判断是否密码也没空格
			if (judge == 0) 
			{
				judge = 0;
				temp = pass.toCharArray();
				for (int i = 0; i < temp.length; i++) 
				{
					if (temp[i] == ' ') 
					{
						//说明此时密码中有空格
						judge = 1;
						break;
					}
				}
			}
			//最终 如果judge == 1 那么返回
			if (judge == 1) 
			{
				Log.i("OfflineLoginDialog", "judge 用户名或密码中有空格");
				return 2;
			}
		}
		
	    /*
	     * 此时已经判断输入没有空格 那么继续判断
	     * 如果输入过短
	     */
		if (user.length() < 8 || pass.length()<8) 
		{
			Log.i("OfflineLoginDialog", "judge 用户名或密码长度过短");
			return 1;
		}
		//此时验证成功
		return 3;
	}
	
	
	/**
	 * 该类实际上是一个线程
	 * 用于进行登陆验证
	 * @author 
	 */

	 class Login extends AsyncTask<Bundle, Integer, Integer>
	 {

		@Override
		protected Integer doInBackground(Bundle... arg0) {
			// TODO Auto-generated method stub
			Bundle bundle = arg0[0];
			String userTmp = (String)bundle.get("Log_username");
			String passTmp = (String)bundle.get("Log_password");
			if (userTmp.equals(ConUtils.default_username) && userTmp.equals(ConUtils.default_password)) 
			{
				return 1;
			}
			BasicNameValuePair pair1 = new BasicNameValuePair("Log_username", userTmp);
			BasicNameValuePair pair2 = new BasicNameValuePair("Log_password", passTmp);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(pair1);
			parameters.add(pair2);
			if (parameters != null)
			{
				HttpClient client = new DefaultHttpClient();
				UrlEncodedFormEntity entity;
				try {
					entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
					HttpPost post = new HttpPost(ConUtils.login_url);
					post.setEntity(entity);
					//client.execute(post);
					//String page = client.execute(post,new BasicResponseHandler());
					HttpResponse response = client.execute(post);
					//mProgress = ProgressDialog.show(LoginActivity.this,"" , "正在登陆");
					int code = response.getStatusLine().getStatusCode();
					Log.i("LoginActivity", "code: "+code);
					InputStream in = response.getEntity().getContent();
					if (code == 200) 
					{
						return 1;
					}
					else
					{
						return 0;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return 0;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == 1) 
			{
				Log.i("OfflineLoginDialog", "登陆成功");
				mProgress.dismiss();
				Toast.makeText(parentContext, "登陆成功",Toast.LENGTH_LONG).show();
			}
			else
			{
				Log.i("OfflineLoginDialog", "登陆失败");
				mProgress.dismiss();
				Toast.makeText(parentContext, "登陆失败",Toast.LENGTH_SHORT).show();
						
			}
		}
		
	 }	
}
