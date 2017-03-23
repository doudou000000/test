package com.test.test_android.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class CustomView extends View{

	Paint mPaint;
	
	int screenWidth;
	int screenHeight;
	
	int radius = 100;
	
	int cX;
	int cY;
	
	double kStarX;
	double kStarY;
	
	double kEndX;
	double kEndY;
	
	int largeLine = 20;
	int smallLine = 10;
	
	float moveX;
	
	float moveY;
	
	public CustomView(Context context) {
		super(context);
		init();
	}

	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	private void init() {
		WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		cX = screenWidth/2;
		cY = screenHeight/2;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);		
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(1f);
		mPaint.setColor(Color.RED);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		 //绘制圆角的矩形   
        RectF re31 = new RectF(10,240,70,270);  
        canvas.drawRoundRect(re31, 15, 15, mPaint);
      //定义一个Path对象，封闭一个三角形   
        Path path = new Path();  
        path.moveTo(200,200);
        //设置辅助点坐标 300，200       终点坐标400，200

        path.quadTo(moveX, moveY, 400, 200); 
        //根据Path进行绘制，绘制三角形   
        canvas.drawPath(path, mPaint);  
		canvas.drawCircle(cX, cY, radius, mPaint);
		
		for(int i = 0; i < 60; i++){
			
			kStarX = radius*Math.cos(Math.PI/180*i*6);
			kStarY = radius*Math.sin(Math.PI/180*i*6);
			
			if(i%5 == 0){	
				kEndX = kStarX + largeLine*Math.cos(Math.PI/180*i*6);
				kEndY = kStarY + largeLine*Math.sin(Math.PI/180*i*6);
			}else{
				kEndX = kStarX + smallLine*Math.cos(Math.PI/180*i*6);
				kEndY = kStarY + smallLine*Math.sin(Math.PI/180*i*6);
			}
			
			kStarX+=cX;
			kStarY+=cY;
			kEndX+=cX;
			kEndY+=cY;
			
			canvas.drawLine((float)kStarX, (float)kStarY, (float)kEndX, (float)kEndY, mPaint);
			
		}
		canvas.drawLine(cX, cY, cX, cY-radius, mPaint);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			
			moveX = event.getX();
			
			moveY = event.getY();
			
			invalidate();
			
			break;

		default:
			break;
		}
		return true;
	}
	
}
