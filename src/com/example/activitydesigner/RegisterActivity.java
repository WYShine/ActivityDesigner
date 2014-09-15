package com.example.activitydesigner;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends Activity
{
	Fragment newFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.fragment_frame);   
		 resInitial();
	}
	
	 /**
     * 资源初始化
     */
    private void resInitial()
    {	
    	newFragment = new  Register_1Fragment();
    	getFragmentManager().
    	beginTransaction().
    	replace(R.id.content, newFragment).
    	commit();
    }
    /**
	 *    各个fragment间的切换
	 */
	public void switchContent(Fragment fragment) {
		newFragment = fragment;
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.content,fragment)
		.commit();
	}
}
 