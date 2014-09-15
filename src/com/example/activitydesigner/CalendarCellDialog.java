package com.example.activitydesigner;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class CalendarCellDialog extends Dialog{

	Context context;
	public CalendarCellDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CalendarCellDialog(Context context,int theme)
	{
		super(context,theme);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_calendar_cell);
	}
}
