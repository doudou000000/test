package com.test.test_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {

	Bitmap background;
	
	public MyGridView(Context context) {
		super(context);
		
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyGridView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bookshelf_layer_center);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		
	
		
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {

		int count = getChildCount();
		int top = count > 0 ? getChildAt(0).getTop() : 0;
		
		int w = getWidth();
		
		int h = getHeight();
		
		for(int y = top;y<h;y+=background.getHeight() ){
			for(int x = 0;x<w;x+=background.getWidth()){
				canvas.drawBitmap(background, x,y,null);
			}
		}
		
		super.dispatchDraw(canvas);
	}
}
