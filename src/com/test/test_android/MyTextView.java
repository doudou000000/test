package com.test.test_android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class MyTextView extends TextView {
	
	private final String namespace = "http://www.angellecho.com/";
	
	private Paint paint;
	
	private float textShowWidth;
	
	private float textShowHight;
	
	private String text;
	
	private int textSize;
	
	private float paddingLeft;
	private float paddingRight;
	private float paddingTop;
	private float paddingBottom;

	
	public MyTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		textSize = 80;
		paddingLeft = attrs.getAttributeIntValue(namespace, "paddingLeft", 0);
		paddingRight = attrs.getAttributeIntValue(namespace, "paddingRight", 0);
		paddingTop = attrs.getAttributeIntValue(namespace, "paddingTop", 0);
		paddingBottom = attrs.getAttributeIntValue(namespace, "paddingBottom", 0);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(textSize);
		paint.setAntiAlias(true);
	    //取得窗口属性    
		DisplayMetrics  dm = new DisplayMetrics();    
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);        
		textShowWidth = dm.widthPixels-paddingLeft-paddingRight;
		textShowHight = dm.heightPixels-paddingTop-paddingBottom;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int charWidth = 0;
		
		int lineCount = 0;
		float drawWidth = 0;
		text = this.getText().toString();
		char[] textArray = text.toCharArray();
		for(int i = 0;i < textArray.length;i++){
			charWidth = 100;
			if(textShowWidth - drawWidth < charWidth){
				lineCount ++;
				drawWidth = 0;
				if((lineCount+1)*(charWidth) > (textShowHight)){
					break;
				}
			}
			canvas.drawText(textArray, i, 1, drawWidth+10, (lineCount+1)*(textSize+10), paint);

			drawWidth += charWidth;

		}
		setHeight((lineCount+1)*textSize);
	} 
}
