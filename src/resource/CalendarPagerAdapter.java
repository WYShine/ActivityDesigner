package resource;


import com.example.activitydesigner.CalendarPagerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;


public class CalendarPagerAdapter extends FragmentStatePagerAdapter {

	View cellView;
	public CalendarPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return CalendarPagerFragment.create(position);
	}

	@Override
	public int getCount() {
		int years = LunarCalendar.getMaxYear() - LunarCalendar.getMinYear();
		return years * 12;
	}
	
	
}
