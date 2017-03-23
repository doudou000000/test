package com.test.test_android;

import java.io.Serializable;
import java.util.List;

public class LiveVideo implements Serializable{
	// @Fields serialVersionUID
	private static final long serialVersionUID = 1L;
	//测试类型，包括： vod（点播，也就是之前的视频播放），livevideo（html5直播）, rtmpup（rtmp上行）、 rtmpdown（rtmp下行） 等， 默认均为livevideo	
    private String platformtype;
    //测试类型， 1： 人工测试； 2： 自动测试； 在人工测试的时候， 只能选择1个直播间地址； 自动的时候可以选择多个
    private String testType;
    //测试（单个视频）时长， 单位秒
    private String singleurltimeout;
    //用户选中的待测试的直播间个数    
    private String urlnumber;
    //待测试的直播间列表
    private List<LiveRoom> urllist;
	public String getPlatformtype() {
		return platformtype;
	}
	public void setPlatformtype(String platformtype) {
		this.platformtype = platformtype;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public String getSingleurltimeout() {
		return singleurltimeout;
	}
	public void setSingleurltimeout(String singleurltimeout) {
		this.singleurltimeout = singleurltimeout;
	}
	public String getUrlnumber() {
		return urlnumber;
	}
	public void setUrlnumber(String urlnumber) {
		this.urlnumber = urlnumber;
	}
	public List<LiveRoom> getUrllist() {
		return urllist;
	}
	public void setUrllist(List<LiveRoom> urllist) {
		this.urllist = urllist;
	}  
}
