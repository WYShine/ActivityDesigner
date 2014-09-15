package com.example.activitydesigner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import resource.ConUtils;
import resource.MyResource;

import MyHttp.JSON;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BackActivity extends Activity{

	ListView listView = null;
	ImageView back = null;
	TextView search = null;
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_back);
		//根据用户ID 获取其以往活动信息
		String tmp = MyResource.Log_username;
		backInfoAccess access = new backInfoAccess();
		access.execute(tmp);
		resInitial();
		data = getdata();
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Log.i("BackActivity", "cancel onClick 调用成功");
				BackActivity.this.finish();
			}
		});
		
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Log.i("BackActivity", "search onClick 调用成功");
			}
		});
		
		
		SimpleAdapter adapter = new SimpleAdapter
				(getApplicationContext(),data, R.layout.simple_list_item_1,
						new String[]{"activity_name"}, new int[]{R.id.item_title});
		listView.setAdapter(adapter);
	}
	
	
	/**
	 * 相关资源初始化
	 */
	private void resInitial()
	{
		listView = (ListView)findViewById(R.id.back_listview);
		back = (ImageView)findViewById(R.id.back_back);
		search = (TextView)findViewById(R.id.back_search);
	}
	
		private List<HashMap<String,Object>> getdata()
	{
		List<HashMap<String,Object>> listTemp = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String demo_1 = "2013/12/14/软件学院英语晚会";
		String demo_2 = "2013/09/15/软件学院迎新晚会";
		String demo_3 = "2014/05/15/软件学院配音比赛";
		String empty = "  ";
		map.put("activity_name", demo_1);
		listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put("activity_name", demo_2);
		listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put("activity_name", demo_3);
		listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put("activity_name", empty);
		listTemp.add(map);
		listTemp.add(map);
		listTemp.add(map);
		listTemp.add(map);
		return listTemp;
	}
	
	/**
	 * 获取 以往活动 相关信息
	 * @Integer 用户ID
	 */
	class backInfoAccess extends AsyncTask<String, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Log.i("BackActivity", "backInfoAccess doInBackground...");
			String ID = arg0[0];
			BasicNameValuePair pair1 = new BasicNameValuePair("Log_username",ID);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(pair1);
			if (parameters != null) {
				HttpClient client = new DefaultHttpClient();
				UrlEncodedFormEntity entity;
			try {
				 entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
				 HttpPost post = new HttpPost(ConUtils.backInfo_url);
				 post.setEntity(entity);
				 client.execute(post);
				 HttpResponse response = client.execute(post);
				 HttpEntity resultEntity = response.getEntity();
				 //Log.i("BackActivity",  );
				 //InputStream in = response.getEntity().getContent();//服务器返回的数据
				 //int code = response.getStatusLine().getStatusCode();
				 if (resultEntity != null) 
				 {
					BufferedReader br = new BufferedReader(new InputStreamReader
							(resultEntity.getContent(),"utf-8"));
					StringBuffer sb = new StringBuffer();
					String line;
					while((line=br.readLine())!=null)
					{
						sb.append(line);
					}
					br.close();
				 
				 JSON json = new JSON();
				 String key[] = new String[2];
				 String result[];
				 key[0] = "success";
				 key[1] = "activity";
				 result = json.getJSON(sb.toString(),key);//in.toString();
				 if (result == null) 
				 {
					 result = new String[1];
					 result[0] = "结果为空";
				}
				 for (int i = 0; i < result.length; i++) {
					Log.i("BackActivity", "string  "+result[i]);
				}
			 }
				 //Log.i("BackActivity", in.toString());
				 Log.i("BackActivity", "backInfoAccess success!");
				return 1;
				 
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			return null;
		}
		
	}
}