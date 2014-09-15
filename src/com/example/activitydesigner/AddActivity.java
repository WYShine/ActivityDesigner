package com.example.activitydesigner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;



import resource.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class AddActivity extends Activity {

	private ListView  listView1 = null;
	private ListView  listView2 = null;
	private ListView  listView3 = null;
	private ListView  listView4 = null; 
	private TextView finish = null; //完成按钮
	private TextView back = null;  //取消按钮
	ArrayList<HashMap<String, String>> map_list1 = null; 
	ArrayList<HashMap<String, String>> map_list2 = null;
	ArrayList<HashMap<String, String>> map_list3 = null;
	ArrayList<HashMap<String, String>> map_list4 = null;
	AlertDialog MyDialog = null;	//事件处理框
	private EditText info = null;
	AddActivityInfoProccess InfoProcess;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		resInitial();
		getDataSource1();
		getDataSource2();
		getDataSource3();
		getDataSource4();
		setAdapter();
		InfoProcess = new AddActivityInfoProccess();
		listView1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Log.i("AddActivity", "onItemClick 标题");
					showDialog(1);
				}else if(arg2 == 1){
					Log.i("AddActivity", "onItemClick 位置");
					showDialog(2);
				}
			}
			});
		
		listView2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Log.i("AddActivity", "onItemClick 全天");
				}else if(arg2 == 1){
					Log.i("AddActivity", "onItemClick 开始");
					showDialog(3);
				}else if(arg2 == 2){
					Log.i("AddActivity", "onItemClick 结束");
					showDialog(4);
				}
			}
			});
		
		listView3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Log.i("AddActivity", "onItemClick 被邀请人");
					Intent intent = new Intent(getBaseContext(),AddContactActivity.class);
					startActivity(intent);
				}
			}
			});
		
		listView4.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 0) {
					Log.i("AddActivity", "onItemClick 备注");
					showDialog(5);
				}
			}
			});
		
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Log.i("AddActivity", "back onClick 调用成功");
				MyResource.clean();
				AddActivity.this.finish();
			}
		});
		finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.i("AddActivity", "finish onClick invoke success!");
				//此时调用函数进行发送
				String user = MyResource.Log_username;
				InfoProcess.execute(user);
				
			}
		});
	}
	
	/**
	 * 资源初始化
	 */
	private void resInitial()
	{
		listView1 = (ListView)findViewById(R.id.add_list1);
		listView2 = (ListView)findViewById(R.id.add_list2);
		listView3 = (ListView)findViewById(R.id.add_list3);
		listView4 = (ListView)findViewById(R.id.add_list4);
		back = (TextView)findViewById(R.id.add_back);
		finish = (TextView)findViewById(R.id.add_sure);
	}
	
	/**
	 * 数据初始化
	 * @return
	 */
	private ArrayList<HashMap<String, String>> getDataSource1() {

		map_list1 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		map1.put("item", "标题");
		map2.put("item", "位置");
		map_list1.add(map1);
		map_list1.add(map2);

		return map_list1;
	}
	
	private ArrayList<HashMap<String, String>> getDataSource2() {

		map_list2 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		map1.put("item", "全天");
		map2.put("item", "开始");
		map3.put("item", "结束");
		map_list2.add(map1);
		map_list2.add(map2);
		map_list2.add(map3);
		return map_list2;
	}
	
	private ArrayList<HashMap<String, String>> getDataSource3() {

		map_list3 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("item", "被邀请人");
		map_list3.add(map1);

		return map_list3;
	}
	
	private ArrayList<HashMap<String, String>> getDataSource4() {

		map_list4 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("item", "备注");
		map_list4.add(map1);

		return map_list4;
	}
	
	/**
	 * Listview adapeter 设置
	 */
	private void setAdapter()
	{
		SimpleAdapter adapter1 = new SimpleAdapter(
				getApplicationContext(),
				map_list1,R.layout.simple_list_item_1, 
				new String[] { "item" }, new int[] { R.id.item_title });
		listView1.setAdapter(adapter1);
		
		SimpleAdapter adapter2 = new SimpleAdapter(
				getApplicationContext(),
				map_list2,R.layout.simple_list_item_1, 
				new String[] { "item" }, new int[] { R.id.item_title });
		listView2.setAdapter(adapter2);
		
		SimpleAdapter adapter3 = new SimpleAdapter(
				getApplicationContext(),
				map_list3,R.layout.simple_list_item_1, 
				new String[] { "item" }, new int[] { R.id.item_title });
		listView3.setAdapter(adapter3);
		
		SimpleAdapter adapter4 = new SimpleAdapter(
				getApplicationContext(),
				map_list4,R.layout.simple_list_item_1, 
				new String[] { "item" }, new int[] { R.id.item_title });
		listView4.setAdapter(adapter4);
	}
	
	
	
	/**
     * 对话框
     * 
     */
    @Override
    protected Dialog onCreateDialog(int id) 
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
         switch(id)
         {
        	case 1:	builder.setTitle("活动标题");
        			builder.setView(dialogView);
        			//设置确认的事件
        			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
        				
        				@Override
        				public void onClick(DialogInterface arg0, int arg1) {
              
        					//要在此时绑定editText 若在一开始绑定 会跳出空指针异常
        					info = (EditText)dialogView.findViewById(R.id.dialog_info);
        					 MyResource.acName = info.getText().toString();
        					 Log.i("AddActivity", "设置acName(活动标题)："+MyResource.acName);
        				}	
        			});
        			//设置取消的事件
        			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        					@Override
        					public void onClick(DialogInterface arg0, int arg1) {
        						MyDialog.dismiss();
        						}
        					});
        			break;
        	case 2: builder.setTitle("活动位置");
        			builder.setView(dialogView);
        			//设置确认的事件
        			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
        				
        				@Override
        				public void onClick(DialogInterface arg0, int arg1) {
              
        					//要在此时绑定editText 若在一开始绑定 会跳出空指针异常
        					info = (EditText)dialogView.findViewById(R.id.dialog_info);
        					 MyResource.acLocation = info.getText().toString();
        					 Log.i("AddActivity", "设置acLocation(活动位置)： "+MyResource.acLocation);
        				}	
        			});
        			//设置取消的事件
        			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        					@Override
        					public void onClick(DialogInterface arg0, int arg1) {
        						MyDialog.dismiss();
        						}
        					});
        			break;
        	case 3:  builder.setTitle("活动开始时间");
        			 builder.setView(dialogView);
        			 //设置确认的事件
        			 builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				
        				 @Override
        				 public void onClick(DialogInterface arg0, int arg1) {
      
        					 //要在此时绑定editText 若在一开始绑定 会跳出空指针异常
        					 info = (EditText)dialogView.findViewById(R.id.dialog_info);
        					 MyResource.acBeginTime = info.getText().toString();
        					 Log.i("AddActivity", "设置acBeginTime(活动开始时间)： "+MyResource.acBeginTime);
				}	
			});
			//设置取消的事件
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						MyDialog.dismiss();
						}
					});
			break;
        	case 4:	builder.setTitle("活动结束时间");
			 		builder.setView(dialogView);
			 		//设置确认的事件
			 		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			 			@Override
				 	public void onClick(DialogInterface arg0, int arg1) {
					 //要在此时绑定editText 若在一开始绑定 会跳出空指针异常
					 info = (EditText)dialogView.findViewById(R.id.dialog_info);
					 MyResource.acEndTime = info.getText().toString();
					 Log.i("AddActivity", "设置acEndTime(活动结束时间)： "+MyResource.acEndTime);
			 			}	
			 		});
			 		
			 	 //设置取消的事件
			 	 builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			 	 @Override
			 	 public void onClick(DialogInterface arg0, int arg1) {
				 MyDialog.dismiss();
				 }
			 });
			break;
        			
        	case 5: builder.setTitle("活动备注");
        			builder.setView(dialogView);
        			//设置确认的事件
        			builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
        				
        				@Override
        				public void onClick(DialogInterface arg0, int arg1) {
              
        					//要在此时绑定editText 若在一开始绑定 会跳出空指针异常
        					info = (EditText)dialogView.findViewById(R.id.dialog_info);
        					 MyResource.acNotes = info.getText().toString();
        					 Log.i("AddActivity", "设置acNotes(活动备注)： "+MyResource.acNotes);
        				}	
        			});
        			//设置取消的事件
        			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
        					@Override
        					public void onClick(DialogInterface arg0, int arg1) {
        						MyDialog.dismiss();
        						}
        					});
        
        }
         MyDialog = builder.create();
         return MyDialog;
    }
    
    private class AddActivityInfoProccess extends AsyncTask<String, Integer, Integer>
    {

		@Override
		protected Integer doInBackground(String... arg0)
		{
			String user = arg0[0];
			String a = MyResource.acName;
			String b = MyResource.acLocation;
			String c = MyResource.acBeginTime;
			String d = MyResource.acEndTime;
			String e = MyResource.acNotes;
			String f = MyResource.acInvited;
			String g = "date";//MyResource.acDate;
			Log.d("AddActivity",a+" "+b+" "+c+" "+d+" "+e+" "+f+" "+g);
			BasicNameValuePair pair0 = new BasicNameValuePair("Log_username", user);
			BasicNameValuePair pair1 = new BasicNameValuePair("Ac_name", a);
			BasicNameValuePair pair2 = new BasicNameValuePair("Ac_location", b);
			BasicNameValuePair pair3 = new BasicNameValuePair("Ac_beginTime", c);
			BasicNameValuePair pair4 = new BasicNameValuePair("Ac_endTime", c);
			BasicNameValuePair pair5 = new BasicNameValuePair("Ac_note", e);
			BasicNameValuePair pair6 = new BasicNameValuePair("Ac_invited", f);
			BasicNameValuePair pair7 = new BasicNameValuePair("Ac_date", g);
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(pair0);
			parameters.add(pair1);
			parameters.add(pair2);
			parameters.add(pair3);
			parameters.add(pair4);
			parameters.add(pair5);
			parameters.add(pair6);
			parameters.add(pair7);
			if (parameters != null)
			{
				HttpClient client = new DefaultHttpClient();
				UrlEncodedFormEntity entity;
				
					try {
						entity = new UrlEncodedFormEntity(parameters,HTTP.UTF_8);
						HttpPost post = new HttpPost(ConUtils.AddNewActivity_url);
						post.setEntity(entity);
						HttpResponse response = client.execute(post);
						int code = response.getStatusLine().getStatusCode();
						Log.i("LoginActivity", "code: "+code);
						InputStream in = response.getEntity().getContent();
						return 1;
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
			}
			return 0;
		}
		
		@Override
		protected void onPostExecute(Integer result) 
		{
			// TODO Auto-generated method stub
			if (result == 1)
			{
				
				AddActivity.this.finish();
				Toast.makeText(getBaseContext(), "活动已经创建", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getBaseContext(), "向服务器端发送数据失败 请检查网络",Toast.LENGTH_SHORT).show();
			}
		}
    }
}
