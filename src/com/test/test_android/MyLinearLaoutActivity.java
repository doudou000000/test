package com.test.test_android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MyLinearLaoutActivity extends Activity {

	RecyclerView rv;
	
	List<String> dataLists;
	
	MyRecyclerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_recycler_view_layout);
		initDat();
		rv = (RecyclerView)findViewById(R.id.id_recyclerview);
		rv.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MyRecyclerAdapter(this, dataLists);
		rv.setAdapter(adapter);
	}

	private void initDat() {
		dataLists = new ArrayList<String>();
		for(int i = 0; i < 20; i++){
			dataLists.add("第"+i+"条测试数据");
		}
	}
	
}
