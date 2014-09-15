package resource;

import com.example.activitydesigner.CircleContentFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CirclePagerAdapter extends FragmentPagerAdapter{

	public CirclePagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}



	@Override
	public Fragment getItem(int i) {
		// TODO Auto-generated method stub
		Fragment content = new CircleContentFragment();
		
		return content;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
