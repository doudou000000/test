package com.test.test_android;

public class LiveRoom {
	String url;
	String name;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "LiveRoom [url=" + url + ", name=" + name + "]";
	}
	
}
