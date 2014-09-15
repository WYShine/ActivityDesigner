package com.example.activitydesigner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import resource.ConUtils;
import resource.MyResource;

import com.example.activitydesigner.AddContactActivity.MyAdapter;
import com.example.activitydesigner.LoginActivity.login;


import MyHttp.JSON;
import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.AutoText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PresentActivity extends Activity{

	ListView listView = null;
	ImageView back = null; //回退按钮
	ImageView add = null; //添加按钮
	TextView edit_search = null; //搜素按钮
	AutoCompleteTextView edit = null; //搜索框
	List<HashMap<String, Object>> Data = new ArrayList<HashMap<String,Object>>(); 
	String ListTag = "ACTIVITY_NAME";
	String NAME[];
	String dataKey[];
	boolean flag = false;
	Bundle activityBundle;
	Bundle []partBundles;
	int resultDataLength = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_present);
		resInitial();
		activityBundle = new Bundle();
		//此时
		presentInfoAccess present = new presentInfoAccess();
		present.execute(MyResource.Log_username);
			
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("PresentActivity", "cancel onClick 调用成功");
				PresentActivity.this.finish();
			}
			
		});
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Log.i("PresentActivity", "add onClick 调用成功");
				Intent intent = new Intent(getBaseContext(),AddActivity.class);
				startActivity(intent);
			}
		});
		edit_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("PresentActivity", "edit_search onClick 调用成功");
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(),ActivityInfoActivity.class);
				//Log.d("zzzz", activityBundle.getBundle(Integer.toString(postion)).toString());
				intent.putExtras(activityBundle.getBundle(Integer.toString(postion)));
				startActivity(intent);
			}
		});
	}
	
	
	/**
	 * 
	 * 自定义适配器
	 *
	 */
	public class MyAdapter extends BaseAdapter
	{

		LayoutInflater inflater = null;
		public MyAdapter(Context context)
		{
			this.inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.i("PresentActivity", "Data size: "+Data.size());
			return Data.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			ViewHolder holder = null;
			if(convertView == null)
			{
				holder = new ViewHolder();  
				convertView = inflater.inflate(R.layout.simple_list_item_1,null);
				//注意这里是convertview
				holder.itemTextView = (TextView)convertView.findViewById(R.id.item_title);
				convertView.setTag(holder);
				Log.d("PresentActivity", "convertView == null");
			}
			else
			{
				holder = (ViewHolder)convertView.getTag(); 
			}
			
			holder.itemTextView.setText((String)Data.get(position).get(ListTag));
			return convertView;
		}
		
	}
	
	
	private List<HashMap<String, Object>> getdata()
	{
		List<HashMap< String, Object>> listTemp= new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		//获得当前活动的信息
		//Map<String, Boolean> presentActivityTemp = MyResource.presentActivityInfo;
		String name_1 = "2014/03/07/FTC志愿者";
		String name_2 = "2014/04/05/一班春游";
		String name_3 = "2014/04/06/同学聚会";
		String name_empty = "  ";
		boolean myOrNot;
		//map.put(ListTag,NAME[0]);
		//listTemp.add(map);
		for (int i = 0; i < resultDataLength; i++) {
			
			map.put(ListTag, NAME[i]);
			listTemp.add(map);
			map = new HashMap<String, Object>();
		}
		//map = new HashMap<String, Object>();
		//map.put(ListTag,NAME[1]);
		//listTemp.add(map);
		//map = new HashMap<String, Object>();
		//map.put(ListTag,NAME[2]);
		//listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put(ListTag,name_empty);
		listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put(ListTag,name_empty);
		listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put(ListTag,name_empty);
		listTemp.add(map);
		map = new HashMap<String, Object>();
		map.put(ListTag,name_empty);
		listTemp.add(map);
		return listTemp;
	}
	
	private void resInitial()
	{
		listView = (ListView)findViewById(R.id.present_listview);
		back = (ImageView)findViewById(R.id.present_back);
		add = (ImageView)findViewById(R.id.present_add);
		edit_search = (TextView)findViewById(R.id.present_edit_search);
		edit = (AutoCompleteTextView)findViewById(R.id.present_edit);
	}
	
	private final class ViewHolder
	{
		public TextView itemTextView;
		public ImageView itemImageView;
	}
	
	
	
	
	/**
	 * 该类在后台操作 实现数据的载入 
	 * @author lenovo
	 *
	 */
	private class presentInfoAccess extends AsyncTask<String,Integer,Integer>
	{

		ArrayList<HashMap<String, Object>> resultData;
		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Log.i("PresentActivity", "presentInfoAccess doInBackground...");
			String ID = arg0[0];
			BasicNameValuePair pair1 = new BasicNameValuePair("Log_username",ID);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(pair1);
			if (parameters != null) {
				HttpClient client = new DefaultHttpClient();
				UrlEncodedFormEntity entity;
			try {
				 entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
				 HttpPost post = new HttpPost(ConUtils.presentInfo_url);
				 post.setEntity(entity);
				 //client.execute(post);
				 HttpResponse response = client.execute(post);
				 HttpEntity resultEntity = response.getEntity();
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
				 
						 JSON json = new JSON();
						 String key[] = new String[5];
						 key[0] = "Ac_name";
						 key[1] = "Ac_location";
						 key[2] = "Ac_date";
						 key[3] = "Ac_time";
						 key[4] = "Ac_invited";
						 //result = json.getJSON(sb.toString(),key);//in.toString();
						 resultData = json.getArrayList(sb.toString(),key);
						 if (resultData == null) 
						 {
							 //result = new String[1];
							 //result[0] = "结果为空";
							 resultData = new ArrayList<HashMap<String,Object>>();
							 HashMap<String, Object> object = new HashMap<String, Object>();
							 object.put("Ac_name", "活动暂时为空");
							 resultData.add(object);
							 Log.i("PresentActivity", "结果为空");
						 }
						 
					 }
						 //Log.i("BackActivity", in.toString());
				 if (response.getStatusLine().getStatusCode() == 200) 
				 {
					 Log.i("PresentActivity", "backInfoAccess success!");
					 return 1;
					
			     }	 
				 return 0;
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
			
			//此时载入数据成功
			if (result == 1) 
			{
				//根据所得数据
				dataKey = new String[5];
				dataKey[0] = "Ac_name";
				dataKey[1] = "Ac_location";
				dataKey[2] = "Ac_date";
				dataKey[3] = "Ac_time";
				dataKey[4] = "Ac_invited";
				NAME = new String[resultData.size()];
				partBundles = new Bundle[resultData.size()];
				resultDataLength = resultData.size();
				for(int i = 0; i!= resultData.size();++i)
				{
					HashMap<String, Object> tmp = resultData.get(i);
					partBundles[i] = new Bundle();
					for (int j = 0; j < dataKey.length; j++) 
					{
						String tmpStr = (String)tmp.get(dataKey[j]);
						partBundles[i].putString(dataKey[j],tmpStr);
						if (NAME[i] == null) 
						{
							NAME[i] = tmpStr;
						}
						else {
							NAME[i] = NAME[i]+"/"+tmpStr;	
						}
						
					}
					Log.i("name:"+i+"  ", NAME[i]);
					activityBundle.putBundle(Integer.toString(i), partBundles[i]);
				}
				Data = getdata();
				flag = true;
				MyAdapter listItemAdapter = new MyAdapter(getBaseContext());
				listView.setAdapter(listItemAdapter);
			}
			if (result == 0) 
			{
				Toast.makeText(getBaseContext(), "连接失败", Toast.LENGTH_SHORT);
			}
		}
	}
}
