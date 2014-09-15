package resource;

import java.util.ArrayList;
import java.util.List;

import com.example.activitydesigner.CircleContentFragment;
import com.example.activitydesigner.CircleImageItemFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class CircleItemPagerAdapter extends FragmentPagerAdapter{

	
	private List<ImageMeta> imageList;
	 private static final int IMAGE_COUNT_PER_PAGE = 10;
	
	public CircleItemPagerAdapter(FragmentManager fm,ArrayList<ImageMeta> imageList) 
	{
		super(fm);	
		
		this.imageList = imageList;
		//获得一个新的imageList
		List<ImageMeta> imageMetas = new ArrayList<ImageMeta>();
		ImageMeta a1 = new ImageMeta();
		ImageMeta a2 = new ImageMeta();
		imageMetas.add(a1);
		imageMetas.add(a2);
		if (imageList != null) {
			imageList.addAll(imageMetas);
			CircleItemPagerAdapter.this.notifyDataSetChanged();
		
		}
	}

	@Override
	public Fragment getItem(int i)
	{
		// TODO Auto-generated method stub
		 int total = getCount();
	        // if it is the last image and there may be more image
	        if (i == (total - 1) && (total % IMAGE_COUNT_PER_PAGE == 0)) 
	        {
	        	Log.i("CircleItemPagerAdapter", "getItem 最后一张图");
	        }
		Fragment fragment = new CircleImageItemFragment();
		
		return fragment;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		if (imageList != null) 
		{
			return imageList.size();
		}
		return 0;
	}
}
