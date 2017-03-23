package com.test.test_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestRecyclerViewActivity extends Activity {

	Button btn4,btn5,btn6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_recycler_view_list_layout);
		btn4 = (Button)findViewById(R.id.button4);
		btn5 = (Button)findViewById(R.id.button5);
		btn6 = (Button)findViewById(R.id.button6);
		btn4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TestRecyclerViewActivity.this, MyLinearLaoutActivity.class);
				TestRecyclerViewActivity.this.startActivity(intent);
			}
		});
		btn5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TestRecyclerViewActivity.this, MyGrideLayoutActivity.class);
				TestRecyclerViewActivity.this.startActivity(intent);
			}
		});
		btn6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TestRecyclerViewActivity.this, MyStaggeredGridLayoutActivity.class);
				TestRecyclerViewActivity.this.startActivity(intent);
			}
		});
	}
	
}
