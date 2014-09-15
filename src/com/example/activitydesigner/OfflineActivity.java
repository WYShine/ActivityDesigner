package com.example.activitydesigner;


import java.util.Calendar;
import java.util.Map;

import com.baidu.platform.comapi.map.a.o;


import resource.CalendarPagerAdapter;
import resource.DateFormatter;
import resource.LunarCalendar;
import resource.MyResource;
import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class OfflineActivity extends FragmentActivity implements
	OnDateSetListener, OnFocusChangeListener, OnMenuItemClickListener{

	private ViewPager pager;//日历 视图
	private PagerAdapter pagerAdapter; //日历视图 适配器
	private View previousMonth; //上月 按钮
	private View nextMonth; // 下月 按钮
	private View cloud; //云 按钮
	private View list; //下拉 按钮
	private View add; //添加 按钮
	private View cellView; //当前单元格
	private DateFormatter formatter;
	private TextView gregorian;//公历
	private TextView lunar;//农历
	private PopupMenu popup;//显示菜单下拉框
	private int cell_count = 1;
	private Map<String, Boolean> hasReminder;//如果日历中间有提醒 
	
	private int getTodayMonthIndex() {
		Calendar today = Calendar.getInstance();
		int offset = (today.get(Calendar.YEAR) - LunarCalendar.getMinYear())
				* 12 + today.get(Calendar.MONTH);
		return offset;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offline);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
		 formatter = new DateFormatter(this.getResources());
		 resInitial();

		pagerAdapter = new CalendarPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(new simplePageChangeListener());

		pager.setCurrentItem(getTodayMonthIndex());
		 cellView = MyResource.onCreateCellView;
	   // Log.i("sdadssa", cellView.toString());
		Log.i("OfflineActivity", "onCreate invoke success!");
	}

	/*
	 * 资源初始化
	 */
	private void resInitial()
	{
		 //实际上在xml文件中已经定义了onclick的内容 
		 pager = (ViewPager) findViewById(R.id.offline_pager);
		 previousMonth = findViewById(R.id.offline_previousMonth);
		 nextMonth = findViewById(R.id.offline_nextMonth);
		 cloud = findViewById(R.id.offline_cloud);
		 list = findViewById(R.id.offline_list);
		 add = findViewById(R.id.offline_add);
		 gregorian = (TextView) findViewById(R.id.offline_gregorian);
		 lunar = (TextView) findViewById(R.id.offline_lunar);
		
	}
	// 日期单元格点击事件
	public void onCellClick(View v)
	{
		if(v.getTag() != null&& cell_count == 1)
		{
			//Toast.makeText(this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
			//Log.i("cellClick", v.getTag().toString());
			//Dialog dialog = new OfflineAddDialog(this,R.style.dialog,v);
			//dialog.show();
			//cell_count++; 
			
		}
	}
	// 日期对话框选择完成事件
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
	{
		 int offset = (year - LunarCalendar.getMinYear()) * 12 + monthOfYear;
		 pager.setCurrentItem(offset);
	}
	// 日期单元格焦点变化事件
	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		if (!hasFocus)
			return;
		cellView = v;
		LunarCalendar lc = (LunarCalendar) v.getTag();
		CharSequence[] info = formatter.getFullDateInfo(lc);
		lunar.setText(info[1]);
		cell_count = 1; //焦点改变 恢复初值
		Log.i("cell focus", v.getTag().toString() );
	}

	// 标题栏图标点击事件
	public void onMenuImageClick(View v)
	{
		switch (v.getId())
		{
			case R.id.offline_previousMonth:
				pager.setCurrentItem(pager.getCurrentItem() - 1);
				break;
			case R.id.offline_nextMonth:
				pager.setCurrentItem(pager.getCurrentItem() + 1);
				break;
			case R.id.offline_cloud:
				//新建一个登陆窗口
				Toast.makeText(this,"登陆后发现管理更给力", Toast.LENGTH_SHORT).show();
				Dialog dialog = new OfflineLoginDialog(this,R.style.dialog);
				dialog.show();  
				
				break;
			case R.id.offline_today:
				pager.setCurrentItem(getTodayMonthIndex());
				Toast.makeText(this,"回到当前日期所在位置", Toast.LENGTH_SHORT).show();
				break;
			case R.id.offline_list:
				popup = new PopupMenu(this, v);
				popup.setOnMenuItemClickListener(this);
				popup.inflate(R.menu.menu_offline_list);
				popup.show();
				break;
			case R.id.offline_add:
				popup = new PopupMenu(this, v);
				popup.setOnMenuItemClickListener(this);
				popup.inflate(R.menu.menu_offline_add);
				popup.show();
				break;
		}
	}

	// 弹出菜单选项点击事件 menu_offline_list menu_offline_add
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()) {
			//	
			case R.id.menu_offline_list_goto:
				Log.i("OfflineActivity", "onMenuItemClick goto invoke success!");
				int year = (pager.getCurrentItem() / 12)
						+ LunarCalendar.getMinYear();
				int month = pager.getCurrentItem() % 12;
				DatePickerDialog dpd = new DatePickerDialog(
						this,
						android.R.style.Theme_DeviceDefault_DialogWhenLarge_NoActionBar,
						this, year, month, 1);
				dpd.getDatePicker().setCalendarViewShown(false);
				dpd.show();
				return true;
			case R.id.menu_offline_list_back:
				Log.i("OfflineActivity", "onMenuItemClick back invoke success!");
				OfflineActivity.this.finish();
				return true;
			case R.id.menu_offline_add_activity:
				Log.i("OfflineActivity", "onMenuItemClick add invoke success!");
				Dialog dialog = new OfflineAddDialog(this,R.style.dialog,cellView);
				dialog.show();
				return true;
			case R.id.menu_offline_add_remind:
				Log.i("OfflineActivity", "onMenuItemClick remind invoke success!");
				return true;
			default:
				return false;
			}
		}
		
		
		// 月份显示切换事件
		private class simplePageChangeListener extends
				ViewPager.SimpleOnPageChangeListener {
			@Override
			public void onPageSelected(int position) {
				// set title year month
				StringBuilder title = new StringBuilder();
				title.append(LunarCalendar.getMinYear() + (position / 12));
				title.append('-');
				int month = (position % 12) + 1;
				if (month < 10) {
					title.append('0');
				}
				title.append(month);
				gregorian.setText(title);
				lunar.setText("");

				// set related button's state
				if (position < pagerAdapter.getCount() - 1
						&& !nextMonth.isEnabled()) {
					nextMonth.setEnabled(true);
				}
				if (position > 0 && !previousMonth.isEnabled()) {
					previousMonth.setEnabled(true);
				}
			}
		}

		public  void switchActivity()
		{
			Intent intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
		}
}
