package com.example.activitydesigner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Templates;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.helpers.NamespaceSupport;

import resource.ConUtils;
import resource.MyResource;

import com.baidu.a.a.a.a.c;
import com.baidu.platform.comapi.map.a.n;

import MyHttp.MyHttp;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	TextView user  = null;
	TextView pass = null;
	Button sure = null;
	Button back = null;
	boolean success = false;//判断是否登陆成功
	boolean end = false; //判断登陆验证是否结束
	String username;//用户名
	String password;//密码
	login login;
	ProgressDialog mProgress;	//发送中。。。
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		resInitial();
		mProgress = new ProgressDialog(this);
		login = new login();
		back.setOnClickListener(new OnClickListener() {
	    	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("LoginActivity", "cancel onTouch 调用成功");
				MyResource.loginState = 0;
				MyResource.Log_username = " ";
				MyResource.clean();
				LoginActivity.this.finish();
			}
		});
	    
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("LoginActivity", "sure onClick 调用成功");
				username = user.getText().toString();
				password = pass.getText().toString();
				int judgeResult = judge(username, password);
				//对账号密码进行输入判断
				if (judgeResult == 0) 
				{
					//此时用户名或密码为空
					Toast.makeText(LoginActivity.this, "用户名或密码不能为空",Toast.LENGTH_SHORT).show();
				}
				if (judgeResult == 1) 
				{
					//此时用户名或密码长度过短
					Toast.makeText(LoginActivity.this, "用户名或密码长度过短",Toast.LENGTH_SHORT).show();
				}
				if (judgeResult == 2) 
				{
					//此时用户名或密码中有空格
					Toast.makeText(LoginActivity.this, "用户名或密码中有空格",Toast.LENGTH_SHORT).show();
				}
				if (judgeResult == 3) 
				{
				
					Bundle bundle = new Bundle();
					bundle.putString("Log_user", username);
					bundle.putString("Log_pass", password);
					mProgress.setMessage("正在登陆");
					mProgress.show();
					login.execute(bundle);		
					
					if (end == true && success == false) 
					{
						Toast.makeText(LoginActivity.this, "用户名或密码错误",Toast.LENGTH_LONG).show();
					}
				} 
			}
		});
	}
	
	
	/**
	 * 初始化本界面控件
	 */
	private void resInitial()
	{
		user = (TextView)findViewById(R.id.login_user);
		pass = (TextView)findViewById(R.id.login_pass);
		sure = (Button)findViewById(R.id.login_sure);
		back = (Button)findViewById(R.id.login_back);
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
			Log.i("LoginActivity", "judge 用户名或密码为空");
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
				Log.i("LoginActivity", "judge 用户名或密码中有空格");
				return 2;
			}
		}
		
	    /*
	     * 此时已经判断输入没有空格 那么继续判断
	     * 如果输入过短
	     */
		if (user.length() < 8 || pass.length()<8) 
		{
			Log.i("LoginActivity", "judge 用户名或密码长度过短");
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
	class LoginThread extends Thread
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
		   try {
			sleep(3000);
			Validation();
			//mProgress.dismiss();
			end = true;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
		
		/**
		 * 验证登录是否成功
		 * @return
		 * @throws Exception 
		 * @throws ClientProtocolException 
		 */
		private void Validation() throws ClientProtocolException, Exception
		{
			MyHttp myHttp = new MyHttp();
			if (myHttp.loginJudge(username, password))
			{
				success = true;
			}
		}
	}
	
	class login extends AsyncTask<Bundle, Integer, Integer>
	{
		String userTmp;
		String passTmp;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected Integer doInBackground(Bundle... arg0) 
		{
			// TODO Auto-generated method stub
			Bundle data = arg0[0];
			 userTmp = data.getString("Log_user");
			 passTmp = data.getString("Log_pass");
			Log.d(userTmp, passTmp);
			if (userTmp.equals(ConUtils.default_username) && passTmp.equals(ConUtils.default_password)) 
			{
				return 1;
			}
		    //Map<String, Object> pair = new HashMap<String, Object>();
			BasicNameValuePair pair1 = new BasicNameValuePair("Log_username", userTmp);
			BasicNameValuePair pair2 = new BasicNameValuePair("Log_password", passTmp);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			//List<Map<String, Object>>paraList = new ArrayList<Map<String,Object>>();
			parameters.add(pair1);
			parameters.add(pair2);
			//paraList.add(pair);
		
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
					int code = response.getStatusLine().getStatusCode();
					Log.i("LoginActivity", "code: "+code);
					InputStream in = response.getEntity().getContent();
					
					//Log.i("LoginActivity", "state"+strResult);
					if (code == 200)
					{
						//String strResult=EntityUtils.toString(response.getEntity()); 
						//Log.i("LoginActivity", "state: "+strResult);
						TextView textView = (TextView) findViewById(R.id.textView1);
						textView.setText("OK");
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
		protected void onPostExecute(Integer result) 
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == 1)
			{
				mProgress.dismiss();
				login = new login();
				MyResource.loginState = 1;
				MyResource.Log_username = userTmp;
				Intent intent = new Intent(getBaseContext(),MainActivity.class);
				startActivity(intent);
			}
			else
			{
			    login = new login();
				mProgress.dismiss();
				Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT);
			}
		}
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}

