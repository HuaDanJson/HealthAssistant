package com.demo.bs.demoapp2.stepcounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.demo.bs.demoapp2.R;

public class StartActivity extends Activity {

	private TextView runTextView, strongTextView,strong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start);

		runTextView = (TextView)findViewById(R.id.run);
		strongTextView = (TextView)findViewById(R.id.strong);
		strong = (TextView) findViewById(R.id.setting);
		runTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, StepCounterActivity.class);
				Bundle bundle = new Bundle(); 
				bundle.putBoolean("run", false);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		strongTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, StepCounterActivity.class);
				Bundle bundle = new Bundle(); 
				bundle.putBoolean("run", true);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		strong.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			}
		});
	}


}
