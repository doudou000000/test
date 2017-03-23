package com.test.test_android;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParse {

	public List<Person> getParsePersonData(InputStream is) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser parse = null;
		try {
			parse = saxParserFactory.newSAXParser();
			parse.parse(is, new MyHanlder());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	class MyHanlder extends DefaultHandler{
		
		private List<Person> personList;
		
		public List<Person> getPersonList(){
			return personList;
		}

		@Override
		public void startDocument() throws SAXException {
			personList = new ArrayList<Person>();
		}

		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			super.endDocument();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
		}
	}
	
}
