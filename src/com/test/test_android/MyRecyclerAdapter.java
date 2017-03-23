package com.test.test_android;



import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyRecyclerAdapter extends Adapter<MyRecyclerAdapter.MyViewHolder>{

private Context context;
	
	private List<String> dataLists;
	
	public MyRecyclerAdapter(Context context, List<String> dataLists) {
		super();
		this.context = context;
		this.dataLists = dataLists;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return dataLists.size();
	}



	@Override
	public void onBindViewHolder(MyViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.tv.setText(dataLists.get(arg1));
	}



	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.test_recycler_view_item_layout, null));
		return viewHolder;
	}
	
	class MyViewHolder extends ViewHolder{

		TextView tv;
		
		public MyViewHolder(View itemView) {
			super(itemView);
			tv = (TextView)itemView.findViewById(R.id.test_recycler_item_tv);
		}
		
	}
	
}
