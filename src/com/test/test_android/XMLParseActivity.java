package com.test.test_android;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class XMLParseActivity extends Activity implements OnClickListener{

	Button saxBtn,domBtn,pullBtn;
	
	List<Person> personList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xml_parse_layout);
		try {
			initView();
			initlistener();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initView() {
		saxBtn = (Button)this.findViewById(R.id.xml_parse_btn_sax);
		domBtn = (Button)this.findViewById(R.id.xml_parse_btn_dom);
		pullBtn = (Button)this.findViewById(R.id.xml_parse_btn_pull);
	}

	private void initlistener() {
		saxBtn.setOnClickListener(this);
		domBtn.setOnClickListener(this);
		pullBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xml_parse_btn_sax:
			saxParseXml();
			break;
		case R.id.xml_parse_btn_dom:
			domParseXml();
			break;
		case R.id.xml_parse_btn_pull:
			pullParseXml();
			break;

		default:
			break;
		}
	}

	/**
	 * 
	* @Title: pullParseXml 
	* @Description: PULL解析
	* @param     设定文件 
	* @return TODO    返回类型 
	* @throws
	 */
	private void pullParseXml() {
		
		
		
	}

	/**
	 * 
	* @Title: domParseXml 
	* @Description: DOM解析
	* @param     设定文件 
	* @return TODO    返回类型 
	* @throws
	 */
	private void domParseXml() {
		
	}

	/**
	 * 
	* @Title: saxParseXml 
	* @Description: SAX解析
	* @param     设定文件 
	* @return TODO    返回类型 
	* @throws
	 */
	private void saxParseXml() {
		
		try {
			InputStream is = this.getAssets().open("person.xml");
			SAXParse saxParse = new SAXParse();
			personList = saxParse.getParsePersonData(is);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
