package com.test.test_android;

import java.io.Serializable;

public class LiveVideoData implements Serializable{

	// @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	private static final long serialVersionUID = 1L;
	
	private LiveVideo livevideodata;

	public LiveVideo getLivevideodata() {
		return livevideodata;
	}

	public void setLivevideodata(LiveVideo livevideodata) {
		this.livevideodata = livevideodata;
	}
	
	
	
}
