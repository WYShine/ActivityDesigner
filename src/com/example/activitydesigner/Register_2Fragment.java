package com.example.activitydesigner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import resource.ConUtils;
import resource.MyResource;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_2Fragment extends Fragment implements OnClickListener{

	EditText pass; //再次确认密码
	EditText mailAddr; //邮箱地址
	EditText phoneNum; //手机号码
	EditText passQues; //密码提示问题
	EditText passAnsw; //密码提示问题答案
	Button sure; //确认按钮
	Button back; //取消按钮
	Fragment newFragment;
	Bundle bundle;
	ProgressDialog mProgressDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mProgressDialog = new ProgressDialog(getActivity());
		bundle = new Bundle();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_register_2, container,false);
		pass = (EditText)view.findViewById(R.id.register2_pass);
		mailAddr = (EditText)view.findViewById(R.id.register2_mail);
		phoneNum = (EditText)view.findViewById(R.id.register2_phoneNum);
		passQues = (EditText)view.findViewById(R.id.register2_pass_ques);
		passAnsw = (EditText)view.findViewById(R.id.register2_pass_answ);
		sure = (Button)view.findViewById(R.id.register2_sure);
		back = (Button)view.findViewById(R.id.register2_back);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Register_2Fragment", "back onClick invoke success!");
				//切换到上一页
				newFragment = new Register_1Fragment();
				switchFragment(newFragment);
			}
		});
		
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("Register_2Fragment", "sure onClick invoke success!");
				String passTmp = pass.getText().toString();
				String mailAddrTmp = mailAddr.getText().toString();
				String phoneNumTmp = phoneNum.getText().toString();
				String passQuesTmp = passQues.getText().toString();
				String passAnswTmp = passAnsw.getText().toString();
				//进行输入验证
				
				//如果成功
				bundle.putString("Reg_username",resource.MyResource.regUser);
				bundle.putString("Reg_password", passTmp);
				bundle.putString("Reg_mailAddr", mailAddrTmp);
				bundle.putString("Reg_phoneNum", phoneNumTmp);
				bundle.putString("Reg_passQues", passQuesTmp);
				bundle.putString("Reg_passAnsw", passAnswTmp);
				mProgressDialog.setMessage("正在注册");
				mProgressDialog.show();
				Register register = new Register();
				register.execute(bundle);
			}
		});
		
		return view;
	}
	
	private void switchFragment(Fragment fragment)
	{
		if(getActivity() == null)
			return;
		RegisterActivity registerActivity = (RegisterActivity)getActivity();
		registerActivity.switchContent(fragment);
	}
	
	private int judge(String pass_1,String pass_2,String mailAddr,String phoneNum,
			String passQues,String passAnsw)
	{
		//两次输入密码不一致
		if (pass_1 != pass_2)
		{
			return 0;
		}
		return 1;
	}
	
	
	
	
	
	/**
	 * 该类实现了客户端与服务器端数据的通信
	 * @author lenovo
	 *
	 */
	public class Register extends AsyncTask<Bundle,Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Bundle... arg0) {
			// TODO Auto-generated method stub
			Log.i("Register", "doInBackground...");
			Bundle data = arg0[0];
			BasicNameValuePair pair1 = new BasicNameValuePair("Reg_username", (String) data.get("Reg_username"));
			BasicNameValuePair pair2 = new BasicNameValuePair("Reg_password", (String) data.get("Reg_password"));
			BasicNameValuePair pair3 = new BasicNameValuePair("Reg_mailAddr", (String) data.get("Reg_mailAddr"));
			BasicNameValuePair pair4 = new BasicNameValuePair("Reg_phoneNum", (String) data.get("Reg_phoneNum"));
			BasicNameValuePair pair5 = new BasicNameValuePair("Reg_passQues", (String) data.get("Reg_passQues"));
			BasicNameValuePair pair6 = new BasicNameValuePair("Reg_passAnsw", (String) data.get("Reg_passAnsw"));
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(pair1);
			parameters.add(pair2);
			parameters.add(pair3);
			parameters.add(pair4);
			parameters.add(pair5);
			parameters.add(pair6);
			if (parameters != null)
			{
				
			  //对自定义请求头进行URL编码
			  HttpClient client = new DefaultHttpClient();
			  UrlEncodedFormEntity entity;
			  try {
				    entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
					HttpPost post = new HttpPost(ConUtils.register_url);
					post.setEntity(entity);
					//String page = client.execute(post,new BasicResponseHandler());
					HttpResponse response = client.execute(post);
					Log.i("Register", "http process success!");
					int code = response.getStatusLine().getStatusCode();
					InputStream in = response.getEntity().getContent();//服务器返回的数据
					return 1;
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
				Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT);
				mProgressDialog.dismiss();
				MyResource.loginState = 1;
				MyResource.Log_username = MyResource.regUser;
				Intent intent = new Intent(getActivity().getBaseContext(),MainActivity.class);
				startActivity(intent);
			}
			else 
			{
				Toast.makeText(getActivity(), "注册失败", Toast.LENGTH_SHORT);
				mProgressDialog.dismiss();
			}
		}
    }





	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
