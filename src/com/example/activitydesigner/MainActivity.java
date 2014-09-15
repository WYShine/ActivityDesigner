package com.example.activitydesigner;




import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends SlidingActivity {

	Fragment newFragment;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_LEFT_ICON); 
        setContentView(R.layout.fragment_frame);        
        resInitial();
        //getWindow().setFeatureInt(Window.FEATURE_LEFT_ICON,R.layout.activity_main_title);
    }

   
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            //toggle 鏄剧ず
            toggle();
            Log.i("MainActivity","onOptionsItemSelected invoke success!");
            //getSlidingMenu().showMenu();// show menu
            //getSlidingMenu().showContent();//show content
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 璧勬簮鍒濆鍖�
     */
    private void resInitial()
    {	
    	newFragment = new  MainFragment();
    	getFragmentManager().
    	beginTransaction().
    	replace(R.id.content, newFragment).
    	commit();
        
        // set the Behind View
        setBehindContentView(R.layout.fragment_main_left_layout);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment mainLeftFragment = new MainLeftFragment();
        fragmentTransaction.replace(R.id.main_left_fragment,mainLeftFragment);
        
       // fragmentTransaction.replace(R.id.content, new set("Welcome"),"Welcome");
        fragmentTransaction.commit();

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidth(50);
        sm.setShadowDrawable(R.drawable.shadow);
      //setBehindOffset()涓鸿缃甋lidingMenu鎵撳紑鍚庯紝鍙宠竟鐣欎笅鐨勫搴︺�傚彲浠ユ妸杩欎釜鍊煎啓鍦╠imens閲岄潰鍘�:60dp
        sm.setBehindOffset(60);
        sm.setFadeDegree(0.35f);
        //璁剧疆slding menu鐨勫嚑绉嶆墜鍔挎ā寮�
        //TOUCHMODE_FULLSCREEN 鍏ㄥ睆妯″紡锛屽湪content椤甸潰涓紝婊戝姩锛屽彲浠ユ墦寮�sliding menu
        //TOUCHMODE_MARGIN 杈圭紭妯″紡锛屽湪content椤甸潰涓紝濡傛灉鎯虫墦寮�slding ,浣犻渶瑕佸湪灞忓箷杈圭紭婊戝姩鎵嶅彲浠ユ墦寮�slding menu
        //TOUCHMODE_NONE 鑷劧鏄笉鑳介�氳繃鎵嬪娍鎵撳紑鍟�
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

      //浣跨敤宸︿笂鏂筰con鍙偣锛岃繖鏍峰湪onOptionsItemSelected閲岄潰鎵嶅彲浠ョ洃鍚埌R.id.home
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        
       
    }
    
	/**
	 *    鍚勪釜fragment闂寸殑鍒囨崲
	 */
	public void switchContent(Fragment fragment) {
		newFragment = fragment;
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content,fragment)
		.commit();
		getSlidingMenu().showContent();
	}
}

