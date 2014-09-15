package com.example.activitydesigner;

import java.io.BufferedReader;
import java.io.IOException;
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

import com.example.activitydesigner.PresentActivity.MyAdapter;

import resource.ConUtils;
import resource.MyResource;

import MyHttp.JSON;
import android.R.integer;
import android.app.Activity;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MsgInfoActivity extends Activity{

	
	ImageView back = null;
	TextView edit = null;
	ListView listView = null;
	List<HashMap<String,Object>> Data = new ArrayList<HashMap<String,Object>>();
	boolean flag = false;
	MsgInfoAccess msgInfoAccess;
	String dataKey[];
	Bundle activityBundle;
	Bundle []partBundles;
	ArrayList<HashMap<String, Object>> resultData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_msginfo);
		resInitial();
		msgInfoAccess = new MsgInfoAccess();
		msgInfoAccess.execute(MyResource.Log_username);
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MsgInfoActivity","back onClick 调用成功");
				MsgInfoActivity.this.finish();
			}
		});
		
		edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("MsgInfoActivity","edit onClick 调用成功");
			}
		});
		
		
		
	}
	private class MyAdapter extends BaseAdapter
	{

		LayoutInflater inflater = null;
		public MyAdapter(Context context)
		{
			this.inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.i("MsgInfoActivity", "Data size: "+Data.size());
			return Data.size();
		}

		@Override
		public Object getItem(int arg0)
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) 
		{
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
				convertView = inflater.inflate(R.layout.simple_list_item_msginfo,null);
				//注意这里是convertview
				holder.itemName = (TextView)convertView.findViewById(R.id.item_msginfo_name);
				holder.itemDate = (TextView)convertView.findViewById(R.id.item_msginfo_date);
				holder.itemTime = (TextView)convertView.findViewById(R.id.item_msginfo_time);
				holder.itemLocation = (TextView)convertView.findViewById(R.id.item_msginfo_location);
				holder.itemSponsor = (TextView)convertView.findViewById(R.id.item_msginfo_sponsor);
				convertView.setTag(holder);
				Log.d("MsgInfoActivity", "convertView == null");
			}
			else
			{
				holder = (ViewHolder)convertView.getTag(); 
			}
			
			holder.itemName.setText((String)Data.get(position).get("ACTIVITY_NAME"));
			holder.itemDate.setText((String)Data.get(position).get("ACTIVITY_DATE"));
			holder.itemTime.setText((String)Data.get(position).get("ACTIVITY_TIME"));
			holder.itemLocation.setText((String)Data.get(position).get("ACTIVITY_LOCATION"));
			holder.itemSponsor.setText((String)Data.get(position).get("ACTIVITY_SPONSOR"));
			return convertView;
		}
		
	}
	
	private class ViewHolder
	{
		public TextView itemLocation;
		public TextView itemName;
		public TextView itemDate;
		public TextView itemTime;
		public TextView itemSponsor;
	}
	private List<HashMap<String,Object>> getdata()
	{
		List<HashMap<String, Object>> dataTemp = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String titleDemo = "一班春游";
		String dateDemo = "2013/4/5 星期六";
		String timeDemo = "9.00-16.00";
		String locationDemo ="世纪公园";
		String userDemo = "发起人：liu";
	
		//标明有几组数据
		for (int i = 0; i < partBundles.length; i++) 
		{
			//根据数字获得各组数据
			map.put("ACTIVITY_NAME",partBundles[i].getString("Ac_name"));
			map.put("ACTIVITY_DATE",partBundles[i].getString("Ac_date"));
			map.put("ACTIVITY_TIME",partBundles[i].getString("Ac_time"));
			map.put("ACTIVITY_LOCATION",partBundles[i].getString("Ac_location"));
			map.put("ACTIVITY_SPONSOR",partBundles[i].getString("Ac_sponsor"));
			dataTemp.add(map);
			map = new HashMap<String, Object>();
		}	
		for(int i = 0 ; i != 5;i++)
		{
			titleDemo = "  ";
			dateDemo = "  ";
			timeDemo = "  ";
			locationDemo = "  ";
			userDemo = "  ";
			map = new HashMap<String, Object>();
			map.put("ACTIVITY_NAME",titleDemo);
			map.put("ACTIVITY_DATE", dateDemo);
			map.put("ACTIVITY_TIME", timeDemo);
			map.put("ACTIVITY_LOCATION", locationDemo);
			map.put("ACTIVITY_SPONSOR", userDemo);
			dataTemp.add(map);
		}
		return dataTemp;
	}
	
	
	private void resInitial()
	{
		back = (ImageView)findViewById(R.id.msginfo_back);
		edit = (TextView)findViewById(R.id.msginfo_edit);
		listView = (ListView)findViewById(R.id.msginfo_listview);
	}
	
	/**
	 * 该类在后台操作 实现数据的载入 
	 * @author lenovo
	 *
	 */
	
	private class MsgInfoAccess extends AsyncTask<String, Integer, Integer>
	{
		
		@Override
		protected Integer doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			Log.i("BackActivity", "MsgInfoAccess doInBackground...");
			String ID = arg0[0];
			BasicNameValuePair pair1 = new BasicNameValuePair("Log_username",ID);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(pair1);
			if (parameters != null) {
				HttpClient client = new DefaultHttpClient();
				UrlEncodedFormEntity entity;
			try {
				 entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
				 HttpPost post = new HttpPost(ConUtils.MsgInfo_url);
				 post.setEntity(entity);
				 // client.execute(post);
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
					br.close();		 
				 JSON json = new JSON();
				 String key[] = new String[5];
				 String result[];
				 key[0] = "Ac_name";
				 key[1] = "Ac_date";
				 key[2] = "Ac_location";
				 key[3] = "Ac_sponsor";
				 key[4] = "Ac_time";
				 //resultData = json.getArrayList(sb.toString(),key);
				 if (resultData == null) 
				 {
					 HashMap<String, Object> object = new HashMap<String, Object>();
					 object.put("Ac_name", "活动暂时为空");
					 resultData.add(object);
					 Log.i("MsgInfoActivity", "结果为空");
				 }
				 if (response.getStatusLine().getStatusCode() == 200) 
				 {
					 Log.i("MsgInfoActivity", "backInfoAccess success!");
					 return 1;
			     }
				 else
				{
					 return 0;
				}
			  }
			}catch (ClientProtocolException e) {
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
				dataKey[4] = "Ac_sponsor";
				partBundles = new Bundle[resultData.size()];
				activityBundle = new Bundle();
				for(int i = 0; i!= resultData.size();++i)
				{
					HashMap<String, Object> tmp = resultData.get(i);
					partBundles[i] = new Bundle();
					for (int j = 0; j < dataKey.length; j++) 
					{
						String tmpStr = (String)tmp.get(dataKey[j]);
						partBundles[i].putString(dataKey[j],tmpStr);			
					}
					activityBundle.putBundle(Integer.toString(i), partBundles[i]);
				}
				Data = getdata();
				flag = true;
				MyAdapter listItemAdapter = new MyAdapter(getBaseContext());
				listView.setAdapter(listItemAdapter);
			}
			else
			{
				Toast.makeText(MsgInfoActivity.this, "无法载入数据", Toast.LENGTH_SHORT);
			}
		}
	
	}
}
