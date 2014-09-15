package com.example.activitydesigner;



import java.util.ArrayList;
import java.util.HashMap;


import android.R.drawable;
import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CircleFragment extends Fragment implements OnClickListener{
	
	private TextView back;
	private TextView next;
	private ListView  circle_listView = null;
	ArrayList<HashMap<String, Object>> circle_list = null;
	int resArrayIndex = 0;
	int resID[] = {R.drawable.demo_0,R.drawable.demo_0,
			      R.drawable.demo_0,R.drawable.demo_0,
					R.drawable.demo_0,R.drawable.demo_0
					,R.drawable.demo_0,R.drawable.demo_0};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	  View view  = inflater.inflate(R.layout.fragment_circle, container,false);
	  Log.i("CircleFragment", "onCreateView invoke success!");
	  back = (TextView)view.findViewById(R.id.circle_back);
	  back.setOnClickListener(this);
	  next = (TextView)view.findViewById(R.id.circle_next);
	  next.setOnClickListener(this);
	  circle_listView = (ListView)view.findViewById(R.id.circle_listview);
	  circle_list = getData();
	  MyAdapter myAdapter = new MyAdapter(getActivity());
	  circle_listView.setAdapter(myAdapter);
	  return view;
	}
	
	private  ArrayList<HashMap<String, Object>> getData()
	{
		//获取网络上的图片文件
		circle_list = new ArrayList<HashMap<String, Object>>();
		do {
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("img_1",resID[resArrayIndex]);
			resArrayIndex++;
			map1.put("img_2",resID[resArrayIndex]);
			resArrayIndex++;
			map1.put("img_3",resID[resArrayIndex]);
			resArrayIndex++;
			map1.put("img_4",resID[resArrayIndex]);
			resArrayIndex++;
			circle_list.add(map1);
		}while(resArrayIndex <resID.length);
		
		return circle_list;
	}
	
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
			return circle_list.size();
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
		public View getView(int pos, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null)
			{
				holder=new ViewHolder();  
				
				convertView = myInflater.inflate(R.layout.simple_list_item_circle,null);
				holder.pic1 = (ImageView)convertView.findViewById(R.id.item_circle_img1);
				holder.pic2 = (ImageView)convertView.findViewById(R.id.item_circle_img2);
				holder.pic3 = (ImageView)convertView.findViewById(R.id.item_circle_img3);
				holder.pic4 = (ImageView)convertView.findViewById(R.id.item_circle_img4);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder)convertView.getTag();
			}
			holder.pic1.setImageResource(((Integer)( circle_list.get(pos).get("img_1") ) ).intValue());
			holder.pic2.setImageResource(((Integer)( circle_list.get(pos).get("img_2") ) ).intValue());
			holder.pic3.setImageResource(((Integer)( circle_list.get(pos).get("img_3") ) ).intValue());
			holder.pic4.setImageResource(((Integer)( circle_list.get(pos).get("img_4") ) ).intValue());
			return convertView;
		}
		
	}
	/**
	 * Listview 每行控件
	 */
	private class ViewHolder
	{
		ImageView pic1 ;
		ImageView pic2 ;
		ImageView pic3 ;
		ImageView pic4 ;
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.circle_back:
			Log.i("CircleFragment", "circle_back onClick 调用成功!");
			Fragment fragment = new MainFragment();
			MainActivity mainActivity = (MainActivity)getActivity();
			mainActivity.switchContent(fragment);
			break;
		case R.id.circle_next:
			Log.i("CircleFragment", "circle_next onClick 调用成功!");
			break;
		default:
			break;
		}
	}
}
