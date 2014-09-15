package com.example.activitydesigner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


import resource.ContactActivity;
import resource.MyResource;


import MyHttp.SendMsg;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AddContactActivity extends Activity{
	
	ContactActivity contactActivity;     //该类用于查询联系人
	boolean sendSuccessFlag = false; 	//短信是否发送成功
	boolean sendTreadFlag = false;  	//发送短信的进程是否结束
	int selected[] = new int[1000];     //记录listview中的item是否被选择
	ListView listview;					//联系人列表
	//联系人列表中的数据
	List<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();  
	int [] contact_index = new int[1000];  //记录联系人ID
	String phoneNum[] = new String[1000]; //记录联系人电话   应该用MAP记录姓名和电话号码
    String contactName[] = new String[1000];  //记录联系人姓名
    int personCount = 0;				   //记录联系人个数
	Button sure = null;
	Button cancle = null;
	private ProgressDialog mProgress;	//发送中。。。
	AlertDialog customDialog = null;	//发送短信对话框
	Vector<Integer>hasSelected = new Vector<Integer>();
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcontact);
		//初始化资源
		 resInital();
		//构建联系人的数据表
		 Log.d("getdata","1");
		listData = getdata();
		//适配器 类型为自定义的BaseAdapter
		MyAdapter listItemAdapter = new MyAdapter(this);
		listview.setAdapter(listItemAdapter);
		//为ListView中每一个项目设置监听
		listview.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{	
				if (!hasSelected.contains(position+1)) 
				{
					
					selected[personCount] = position+1;
					contact_index[personCount] = position+1;
					Log.d("contact_index", String.valueOf(contact_index[personCount]));
					Log.d("selected", String.valueOf(selected[personCount]));
					hasSelected.add(position+1);
					personCount++;
				}
				else {
					Toast.makeText(getBaseContext(), "已经选中", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	  //为确认按钮设置监听
	  sure.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				phoneNum = getPhoneNum(contact_index,personCount);
				contactName = getPersonName(contact_index,personCount);
				Log.i("AddContactActivity","选好的联系人的个数为："+ personCount);
				//获取联系人的号码之后下面弹出一个对话框进行短信的发送
				showDialog(0);
				
				//成功发送本次短信后 对已经选择的联系人进行清空
				if(sendSuccessFlag == true)
				{
					contactName = null;
					phoneNum = null;
					personCount = 0;
				}
			}
		});
	  
	 //为取消按钮设置监听
	  cancle.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.d("ContactPick","返回");
			AddContactActivity.this.finish();
		}
	});
	  
  } 
	
	/**
	 * 自定义适配器
	 * 便于添加视图和按钮
	 */
	public class MyAdapter extends BaseAdapter
	{
		private LayoutInflater myInflater;
		public MyAdapter(Context context)
		{
			this.myInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listData.size();
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
			public View getView(int position, View convertView, ViewGroup parent)
		    {
				
				ViewHolder holder = null;
				if (convertView == null)
				{
					
					holder=new ViewHolder();  
					
					convertView = myInflater.inflate(R.layout.contact_item,null);
					holder.name = (TextView)convertView.findViewById(R.id.contact_item_username);
					holder.id = (TextView)convertView.findViewById(R.id.contact_item_id);
					convertView.setTag(holder);
					
				}
				else {
					
					holder = (ViewHolder)convertView.getTag();
				}
			
			  holder.name.setText((String)listData.get(position).get("contact_username"));
			  holder.id.setText((String)listData.get(position).get("contact_id"));
				return convertView;
			
		}
		
	}
	
	
	/**
	 * ListView中Item的控件
	 */
	private final class ViewHolder{
		public TextView name;
		public TextView id;
	}
    
 
    /**
     * 实现一个对话框
     * 发送短信界面 
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog, null);
        builder.setTitle("发送短信");
        builder.setView(layout);
        
        //设置确认发送短信按钮的事件
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

        	public void onClick(DialogInterface arg0, int arg1) {
                mProgress = ProgressDialog.show(AddContactActivity.this,"" , "正在发送");
                
                // 新线程，睡眠3秒然后关闭ProgressDialog
                new Thread() {
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            mProgress.dismiss();
                           if(sendTreadFlag == true)
                           {
                        	   //非UI线程刷新UI 在Toast前调用loop.prare之后调用loop.looper 就可以了
                        	   Looper.prepare();  
                        	   //如果发送成功 弹出一个toast进行提示
                        	   Toast.makeText(AddContactActivity.this, "发送完毕", Toast.LENGTH_LONG).show();
                        	   Looper.loop();
                           }
                        }
                    }
                }.start();
                
                for (int i = 0; i < contactName.length; i++) {
           
					MyResource.acInvited = MyResource.acInvited+ contactName[i];
				}
               //调用手机内部发送短信机制 进行发送  
                //电话号码 联系人名字 数量 信息内容
               SendMsg.Send(phoneNum,contactName,personCount,MyResource.MsgIn);
               sendTreadFlag = true;
       		   sendSuccessFlag = true;
            }
        });
        
        //设置取消发送按钮事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
              customDialog.dismiss();
            }
        });
        
        customDialog = builder.create();
        return customDialog;
    }
    
 
    /**
     * 该界面资源初始化
     */
    private void resInital() {
		// TODO Auto-generated method stub
    	
    	sure = (Button)findViewById(R.id.contact_sure);
		cancle = (Button)findViewById(R.id.contact_back);
		listview = (ListView) findViewById(R.id.contact_list);  
		for (int i = 0; i < selected.length; i++) {
			selected[i] = 0;
		}
	}
    
    
    
    /**
	 * 初始化数据 
	 * 将联系人的信息传入一个List中 以便视图的构造
	 * @return list 包含两类map
	 * @return map<contact_username,value>&&map<contact_id,value>
	 */
    public List< HashMap<String, Object> > getdata()
	{
		 String [] id = new String[10000];
		 String [] name = new String[10000];
		 int idTemp;
		 int nameTemp;
		 int index = 0;
		 List<HashMap<String, Object>> listTemp = new ArrayList<HashMap<String, Object>>();
	     //将通讯录返回的内容输入这样一个ArrayList中
		 //Log.d("getdata", "2");
	     Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
	    		 null, null, null, null); 
	     //Log.d("getdata", "3");
	    if(cursor.moveToFirst())
	    {
	    	do
	    	{
	    	
	    		HashMap<String, Object> map = new HashMap<String, Object>();
	    		idTemp = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
	    		nameTemp = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
	    		id[index] = cursor.getString(idTemp);
	    		name[index] = cursor.getString(nameTemp);
	    		map.put("contact_username",name[index]);
	    		map.put("contact_id",id[index]);
	    		listTemp.add(map);
	    		index++;
	    	}while(cursor.moveToNext());
	    }
	    
	     return listTemp;
	}
	
	
	 /**
	 * 通过联系人ID 查询联系人的电话
	 * @return 返回内容为联系人电话的数组
	 */
	public   String[] getPhoneNum(int[] contact_id,int personCount)
	{
		int index = 0;
    	//int i = 0;
    	String [] phoneNum = new String[1000];
    	for(index = 0; index != personCount ; index++)
    	{
    	    String tempString = Integer.toString(contact_id[index]);
    	    Cursor phones = getContentResolver().query
        			(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
        			   ContactsContract.CommonDataKinds.Phone.CONTACT_ID + 
        			   "=" + tempString,null, null );
    	    while(phones.moveToNext())
        	{
        		phoneNum[index] = phones.getString
        		( phones.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER) );
        	}
    	    phones.close();
    	    Log.d("ContactListActivity","查询到的联系人号码为："+ phoneNum[index]);
    	}
    	return phoneNum;
	}
	
	
	 /**
	  * 通过联系人ID 查询联系人的姓名
	  * @return 返回内容为联系人姓名的数组
	  */
	public  String [] getPersonName(int[] contact_id,int personCount)
	{
	    	int index = 0;
	    	String [] PersonName = new String[1000];
	    	for(index = 0; index != personCount ; index++)
	    	{
	    	    String tempString = Integer.toString(contact_id[index]);
	    	    Cursor names = getContentResolver().query
	        			(ContactsContract.Data.CONTENT_URI, null,
	        			   ContactsContract.CommonDataKinds.Identity._ID + 
	        			   "=" + tempString,null, null );
	    	    while(names.moveToNext())
	        	{
	    	    	PersonName[index] = names.getString
	        		( names.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME) );
	        	}
	    	    names.close();
	    	    Log.d("ContactListActivity","查询到的联系人姓名："+ PersonName[index]);
	    	}
	    	return PersonName;
	}
	
	
	
	private class addActivity extends AsyncTask<Bundle, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Bundle... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}