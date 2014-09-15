package com.example.activitydesigner;


import java.util.HashMap;
import java.util.Map;


import resource.CirclePagerAdapter;
import resource.ConUtils;
import MyHttp.UploadUtil;
import MyHttp.UploadUtil.OnUploadProcessListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CircleActivity extends FragmentActivity implements OnClickListener,OnUploadProcessListener{

	/**
	 * 去上传文件
	 */
	protected static final int TO_UPLOAD_FILE = 1;  
	/**
	 * 上传文件响应
	 */
	protected static final int UPLOAD_FILE_DONE = 2;  //
	/**
	 * 选择文件
	 */
	public static final int TO_SELECT_PHOTO = 3;
	/**
	 * 上传初始化
	 */
	private static final int UPLOAD_INIT_PROCESS = 4;
	/** 
	 * 上传中
	 */
	private static final int UPLOAD_IN_PROCESS = 5;
	ViewPager viewPager = null;
	LinearLayout circle;
	LinearLayout my;
	LinearLayout around;
	TextView back;
	TextView pick;
	private String picPath = null;
	ProgressDialog progressDialog;
	//private ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_circle);
		resIntial();
		CirclePagerAdapter adapter = new CirclePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		circle.setOnClickListener(this);
		my.setOnClickListener(this);
		around.setOnClickListener(this);
		back.setOnClickListener(this);
		pick.setOnClickListener(this);
	}
	
	private void resIntial()
	{
		viewPager = (ViewPager)findViewById(R.id.circle_viewPager);
		circle = (LinearLayout)findViewById(R.id.circle_circle);
		my  = (LinearLayout)findViewById(R.id.circle_my);
		around = (LinearLayout)findViewById(R.id.circle_around);
		back  = (TextView)findViewById(R.id.circle_back);
		pick = (TextView)findViewById(R.id.circle_pick);
		progressDialog = new ProgressDialog(this);
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId()) 
		{
			case R.id.circle_back:
				Log.i("CircleActivity", "circle_back onClick invoke success!");
				
				break;
			case R.id.circle_pick:
				Log.i("CircleActivity", "circle_pick onClick invoke success!");
				Intent intent = new Intent(this,CircleImageUploadActivity.class);
				startActivityForResult(intent, TO_SELECT_PHOTO);
				break;
			case R.id.circle_circle:
			Log.i("CircleActivity", "circle_circle onClick invoke success!");
			circle.setSelected(true);
			my.setSelected(false);
			around.setSelected(false);
			viewPager.setCurrentItem(0);
			break;
			case R.id.circle_my:
			Log.i("CircleActivity", "circle_my onClick invoke success!");
			circle.setSelected(false);
			my.setSelected(true);
			around.setSelected(false);
			viewPager.setCurrentItem(1);
			break;
			case R.id.circle_around:
			Log.i("CircleActivity", "circle_around onClick invoke success!");	
			circle.setSelected(false);
			my.setSelected(false);
			around.setSelected(true);
			viewPager.setCurrentItem(2);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
		{
			picPath = data.getStringExtra(CircleImageUploadActivity.KEY_PHOTO_PATH);
			Log.i("CircleActivity", "最终选择的图片="+picPath);
			Bitmap bm = BitmapFactory.decodeFile(picPath);
			//imageView.setImageBitmap(bm);
			if(picPath!=null)
			{
				handler.sendEmptyMessage(TO_UPLOAD_FILE);
			}else{
				Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	/**
	 * 上传服务器响应回调
	 */
	@Override
	public void onUploadDone(int responseCode, String message) {
		progressDialog.dismiss();
		Message msg = Message.obtain();
		msg.what = UPLOAD_FILE_DONE;
		msg.arg1 = responseCode;
		msg.obj = message;
		handler.sendMessage(msg);
	}
	
	private void toUploadFile()
	{
		//uploadImageResult.setText("正在上传中...");
		progressDialog.setMessage("正在上传文件...");
		progressDialog.show();
		//String fileKey = "img";
		UploadUtil uploadUtil = UploadUtil.getInstance();;
		uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", "1211111");
		uploadUtil.uploadFile( picPath,ConUtils.upload_image_TAG, ConUtils.upload_image_url,params);
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TO_UPLOAD_FILE:
				toUploadFile();
				break;
			
			case UPLOAD_INIT_PROCESS:
				//progressBar.setMax(msg.arg1);
				break;
			case UPLOAD_IN_PROCESS:
				//progressBar.setProgress(msg.arg1);
				break;
			case UPLOAD_FILE_DONE:
				String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+UploadUtil.getRequestTime()+"秒";
				Log.i("CircleActivity", "result:  " + result);
				//uploadImageResult.setText(result);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};

	@Override
	public void onUploadProcess(int uploadSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_IN_PROCESS;
		msg.arg1 = uploadSize;
		handler.sendMessage(msg );
	}

	@Override
	public void initUpload(int fileSize) {
		Message msg = Message.obtain();
		msg.what = UPLOAD_INIT_PROCESS;
		msg.arg1 = fileSize;
		handler.sendMessage(msg );
	}
}
