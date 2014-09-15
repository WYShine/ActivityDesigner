package com.example.activitydesigner;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.http.client.ClientProtocolException;

import com.baidu.location.f;



import resource.ImageMeta;
import resource.ImageScrollView;
import resource.ImageScrollView.ImageViewClickListener;
import resource.ImageScrollView.ScrollViewListenner;
import MyHttp.Upload;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class CircleContentFragment extends Fragment{

	ImageScrollView scrollView = null;
	private int page = 0; //当前页面个数
	private boolean loading =  false;  //是否载入完毕
	private ArrayList<ImageMeta> imageList; //存储照片信息的数组
    protected static final int IMAGE_COUNT_PER_PAGE = 10; //每个页面加载10张图片
    File file;
    private  Queue<ImageTask> taskQueue = new LinkedList<ImageTask>();
    public static String IMAGE_VIEW_ID_TAG="ActivityDesigner_IMAGE_VIEW_ID_TAG";
    public static String IMAGE_META_LIST_TAG = "ActivityDesigner_IMAGE_META_LIST_TAG";
    public static String CURRENT_ITEM_TAG = "ActivityDesigner_CURRENT_ITEM_TAG";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//进行图片的载入
		Log.i("CircleContentFragment","onCreate invoke success!");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_circle_content, container,false);
		scrollView = (ImageScrollView) view.findViewById(R.id.circle_content_scrollView);
		imageList = new ArrayList<ImageMeta>();
		ImageMeta a = new ImageMeta("x",300,300,"上海","x",2.0,3.0,"x04","xx05");
			imageList.add(a);
	    onGetScrollView();
		addImageViewList(imageList);
		//loadImageMeta();
		try {
			loadImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (savedInstanceState == null) {
			
		}
		else
		{
			imageList = savedInstanceState
                    .getParcelableArrayList(IMAGE_META_LIST_TAG);
		}
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	/**
	 * 在获得scollview时
	 * 进行相关初始化
	 */
	 public void onGetScrollView() 
	 {
	     if (scrollView == null) 
	     {
	         throw new IllegalStateException();
	     }
	    scrollView.setScrollViewListener(new MyScrollViewListenner());
	    scrollView.setImageViewClickListener(new MyImageViewClickListener());
	 }
	 
	 class MyScrollViewListenner implements ScrollViewListenner
	 {

		@Override
		public void onScrollChanged(ImageScrollView scrollView, int x, int y,
				int oldx, int oldy)
		{
			// TODO Auto-generated method stub
			Log.i("CircleContentFragment", "onScrollChanged invoke success!");
			 View view = (View) scrollView
	                    .getChildAt(scrollView.getChildCount() - 1);
			 int diff = (view.getBottom() - (scrollView.getHeight() + scrollView
	                    .getScrollY()));
			  /* If we reach the bottom and is not loading currently */
			 if (diff == 0 && !loading) 
			 {
				loading = true;
	            if ((imageList.size() % IMAGE_COUNT_PER_PAGE == 0)) 
	            {
	                    ++page;
	                   // mLoader.loadCityMetaList("上海", mPage,
	                      //      IMAGE_COUNT_PER_PAGE, null);
	            }
	            
	          }
		}
			
	 }
	 
	 class MyImageViewClickListener implements ImageViewClickListener
	 {

		@Override
		public void onImageViewClicked(ImageView imageView, int index) 
		{
			// TODO Auto-generated method stub
			Log.i("CircleContentFragment", "onImageViewClicked invoke success!");
			Activity activity = getActivity();
			
			//打开ImageItemActivity
			Intent intent = new Intent(activity.getBaseContext(),CircleImageItemActivity.class);
			intent.putParcelableArrayListExtra(
	                    IMAGE_META_LIST_TAG, imageList);
			 intent.putExtra(CURRENT_ITEM_TAG, index);//将当前索引传递过去
			startActivity(intent);
		}
	 }
	 
	 private void loadImage() throws IOException
	 {
		 ImageTask task;
		 do
		 {
			task = taskQueue.poll();
			if (task != null) 
			{
				ImageView imageView = (ImageView) scrollView.findViewById(task.imageViewId);
			 	Bitmap  bitmap = BitmapFactory.decodeResource(getActivity().getBaseContext().getResources(), 
			 			R.drawable.demo_2);
			 	if (imageView != null) 
			 	{
			 		Log.i("CircleContentFragment", "loadImage 有任务" );
			 		imageView.setImageBitmap(bitmap);
			 		// imageView.setImageResource(task.imageViewId);
			 		
			 		file = new File(Environment.getExternalStorageDirectory()+ "/ShareSight/cache/test.png");
			 		file.createNewFile();
			 		Log.i("xxxxxxxxx", "xxxxxxxxxxxxx");
			 		ByteArrayOutputStream bos = new  ByteArrayOutputStream();
			 		bitmap.compress(CompressFormat.PNG,0, bos);
			 		byte[] bitmapData = bos.toByteArray();
			 		Log.i("xxxxxxxxx", "yyyy");
			 		FileOutputStream fos = new FileOutputStream(file);
			 		Log.i("xxxxxxxxx", "zzzz");
			 		fos.write(bitmapData);

			 	}
			}
		 	
	 	}while(task != null);	 
	 }
	 

	 	private void loadImageMeta()
	 	{
		
	 		ImageTask task = taskQueue.poll();

	        // If the image meta list is empty, then there is no task...
	        if (task == null) 
	        {
	        	Log.i("CircleContentFragment", "loadImageMeta 没有任务");
	            return;
	        }
	        Bundle data = new Bundle();
	        data.putInt(IMAGE_VIEW_ID_TAG, task.imageViewId);
	 	}
	 	
	 private static class ImageTask 
	 {
	 	  int imageViewId;
	 	  String url;

	 	  public ImageTask(int imageViewId) 
	 	  {
	 	       this.imageViewId = imageViewId;
	 	  }
	 }
	 
	 	/**
	     * 向当前界面添加图片, 首先, 初始化整个界面, 然后
	     * 一个个添加图片。
	     * 
	     * @param imageMetaList
	     */
	    private void addImageViewList(List<ImageMeta> imageMetaList)
	    {
	        for (ImageMeta imageMeta : imageMetaList) 
	        {
	            int retId = scrollView.addImageView(imageMeta.getWidth(),
	                   imageMeta.getHeight());
	        	taskQueue.add(new ImageTask(retId));
	          }
	    	
		}   
}
