package com.test.test_android;

import android.app.Activity;
import android.os.Bundle;

public class TextActivity extends Activity{

	MyTextView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.text_layout);
        view = (MyTextView) findViewById(R.id.textView1);
		 String str = "看到实际交付时间看对方身上看见你时开始上课课件设计，伤口会尽快康复和经济。是客户即可拿手机，送快递号开始算扣除搜可是你开始，什么的技术数据技术上的时间空间。山东会计技能介绍，上的坚持科技成就。看到实际交付时间看对方身上看见你时开始上课课件设计，伤口会尽快康复和经济。是客户即可拿手机，送快递号开始算扣除搜可是你开始，什么的技术数据技术上的时间空间。山东会计技能介绍，上的坚持科技成就。"
		 		+ "看到实际交付时间看对方身上看见你时开始上课课件设计，伤口会尽快康复和经济。是客户即可拿手机，送快递号开始算扣除搜可是你开始，什么的技术数据技术上的时间空间。山东会计技能介绍，上的坚持科技成就。看到实际交付时间看对方身上看见你时开始上课课件设计，伤口会尽快康复和经济。是客户即可拿手机，送快递号开始算扣除搜可是你开始，什么的技术数据技术上的时间空间。山东会计技能介绍，上的坚持科技成就。"
		 		+ "看到实际交付时间看对方身上看见你时开始上课课件设计，伤口会尽快康复和经济。是客户即可拿手机，送快递号开始算扣除搜可是你开始，什么的技术数据技术上的时间空间。山东会计技能介绍，上的坚持科技成就。看到实际交付时间看对方身上看见你时开始上课课件设计，伤口会尽快康复和经济。是客户即可拿手机，送快递号开始算扣除搜可是你开始，什么的技术数据技术上的时间空间。山东会计技能介绍，上的坚持科技成就。";

		 view.setText(str);
	}
	
}
