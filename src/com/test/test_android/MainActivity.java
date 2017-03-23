package com.test.test_android;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.google.gson.Gson;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	WebView testWeb;
	ProgressBar pb;
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
	private FrameLayout videoview;// 全屏时视频加载view
	
	BaseAdapter adapter;
	
	Handler h = new Handler(){
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:
				String js = "javascript: var v=document.getElementsByTagName('video')[0];"
						+"v.controls = '';"
						+ "window.getUrl.getRealUrl(v.controls);";
				testWeb.loadUrl(js);
				break;
			case 2:
                executeJavaScriptCode();
				break;
			case VIDEO_REAL_TIME_DATA_INFO:
				realTimeDataInfo();
				break;
			default:
				break;
			}
			
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		try {

			initView();
			initData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private boolean isStop = true;
	
	private void initView() {
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		videoview = (FrameLayout) findViewById(R.id.video_view);
		testWeb = (WebView)findViewById(R.id.webView1);
		pb = (ProgressBar)findViewById(R.id.progressBar1);
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		btn3 = (Button)findViewById(R.id.button3);
		btn4 = (Button)findViewById(R.id.button4);
		btn5 = (Button)findViewById(R.id.button5);
		btn6 = (Button)findViewById(R.id.button6);
		btn7 = (Button)findViewById(R.id.button7);
		btn8 = (Button)findViewById(R.id.button8);
		btn9 = (Button)findViewById(R.id.button9);
//		new Custom(testWeb, pb, this);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, BookActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, TextActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, TestRecyclerViewActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		btn4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, XMLParseActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		btn5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, CustomViewAcitvity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		
		btn6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, TestLauncherActivity.class);
				MainActivity.this.startActivity(intent);
			}
		});
		btn7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				parseJson();
			}

		});
		btn8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TestMapActivity.class);
				MainActivity.this.startActivity(intent);
			}

		});
		btn9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TestCustomEditextActivity.class);
				MainActivity.this.startActivity(intent);
			}

		});
//		{"livevideodata":{"platformtype":"livevideo","testType":"2","singleurltimeout":"60","urlnumber":"1","urllist":[{"name":"正方形打野创始人大司马瓜皮讲师开课了","url":"https://m.douyu.com/606118"}]}}	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isStop){
					try {
						Thread.sleep(1000);
						h.sendEmptyMessage(1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					while(d){
//						
//					}
//					Thread.sleep(1000*60);
//					h.sendEmptyMessage(2);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//				
//				
//			}
//		}).start();
		realTimeData();
	}
 

	Thread t = new Thread(new Runnable() {
		
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(500);
					h.sendEmptyMessage(2);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		}
	});
	
	private void parseJson() {
		
		LiveVideo liveVideo = new LiveVideo();
		
		LiveVideoData liveVideoData = new LiveVideoData();
		
		liveVideo.setPlatformtype("livevideo");
		
		liveVideo.setTestType("2");
		
		liveVideo.setSingleurltimeout("60");
		
		liveVideo.setUrlnumber("1");
		
		List<LiveRoom> urllist = new ArrayList<LiveRoom>();
		LiveRoom liveRoom = new LiveRoom();
		liveRoom.setName("正方形打野创始人大司马瓜皮讲师开课了");
		liveRoom.setUrl("https://m.douyu.com/606118");
		urllist.add(liveRoom);
		liveVideo.setUrllist(urllist);
		
		liveVideoData.setLivevideodata(liveVideo);
		
		String json = new Gson().toJson(liveVideoData);
		
		System.out.println(json);
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isStop = false;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void initData() {
		testWeb.getSettings().setSupportZoom(true);
		testWeb.getSettings().setBuiltInZoomControls(true);
		testWeb.getSettings().setUseWideViewPort(true);
		testWeb.getSettings().setJavaScriptEnabled(true);
		testWeb.getSettings().setDomStorageEnabled(true); 
//		testWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		//为webview设置自定义WebChromeClient和WebViewClient
		testWeb.setWebViewClient(new MyVideoWebViewClient());	
		testWeb.setWebChromeClient(new MyVideoWebChromeClient());
		testWeb.addJavascriptInterface(new GetUrlJavaScriptInterface(), "getUrl"); 
		testWeb.addJavascriptInterface(new addJavaScriptInterface(), "demo"); 
//		testWeb.loadUrl("http://data.video.qiyi.com/videos/other/20160921/cb/98/760b01e69383c01906d56299c03664b0.mp4?pv=0.2");
		testWeb.loadUrl("https://v.qq.com/x/cover/5uaddbgn91womet.html");
//		testWeb.loadUrl("http://www.le.com/ptv/vplay/28091422.html");

//		testWeb.loadUrl("http://183.203.65.157/vhot2.qqvideo.tc.qq.com/z0385w0j5dt.mp4?sdtfrom=v3010&amp;guid=38a25c7bffa0006326b25ba0b15f0915&amp;vkey=F85FE1723735BD574870AE1D6C94D51052F0F07F5912352962F36534AB3903B6A58A8EE90AA3152EBEC24EA2575EA4E9744EDD1D87D39F711FB2ADF1CCD6A174147B18387091E3659CBF82D5BDC628EDE7185C731C9D8706");	
//		testWeb.loadUrl("http://211.136.90.52/v1.html");	
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				getRealUrl("http://hot.vrs.sohu.com/ipad3609279_4690699050521_6810590.m3u8?vid=3609279&uid=1703131222076039&plat=17&SOHUSVP=AcQXfwimFJSerEFNtBUNQ1uhGtWORO0D1dpH_eoel40&pt=5&prod=h5&pg=1&eye=0&cv=1.0.0&qd=68000&src=11060001&ca=4&cateCode=101&_c=1&appid=tv&oth=&cd=");
//			}
//		}).start();
		
	}
	
	class addJavaScriptInterface {
	       
		addJavaScriptInterface() {}

		@JavascriptInterface
		public void play() { 
			System.out.println("========play===播放=");
        } 
		
		@JavascriptInterface
		public void timeupdate() { 
			System.out.println("========play===缓冲=");
        } 
	}
	
	/**
	* @Title: onKeyDown
	* @Description:
	* @see android.support.v4.app.FragmentActivity#onKeyDown(int, android.view.KeyEvent)
	*/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			// 创建退出对话框  
            AlertDialog.Builder isExit = new AlertDialog.Builder(this);  
            // 设置对话框标题  
            isExit.setTitle("系统提示");  
            // 设置对话框消息  
            isExit.setMessage("确定要停止测试吗？");  
            // 添加选择按钮并注册监听  
            isExit.setNegativeButton("停止", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
				    MainActivity.this.finish();
				}
			});
            isExit.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
            // 显示对话框  
            isExit.show();
		}
		return false;
	}
	
	
	/**
	 * @title MyWebViewClient
	 * @description 自定义webview
	 * @author chengpf
	 *
	 */
	boolean huajiao = true;
	String temUrl;
	class MyVideoWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			if(url.startsWith("http")){
//				view.loadUrl(url);
////				return true;
//			}
//
//			System.out.println("=========videoSrc=======onPageStarted=="+url);
//			if (url.contains("leclient")||url.contains("ifengvideoplayer")||url.contains("letvclient")||url.contains("xunlei")) {
////				view.loadUrl(temUrl);
//				return true;
//			}
//			if(url.contains("huajiao") && huajiao){
//				huajiao = false;
//				temUrl = url;
//			}
//			view.loadUrl(temUrl);
			return false;
		}

		
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			System.out.println("=========videoSrc=======onPageStarted=="+url);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
//			if(!d){
//				executeJavaScriptCode();
//			}
//			d = false;
			System.out.println("=========onReceivedTitle=onPageFinished=="+url);
		}
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			System.out.println("=========onReceivedTitle=onReceivedError=="+failingUrl);
		}
		
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
			System.out.println("================"+url);
		}
		
	}
	
	
	
	private void executeJavaScriptCode() {
		String js = "javascript: var v=document.getElementsByTagName('video')[0];"
				+ "v.addEventListener('play',function(){window.demo.play();},true);"
				+ "v.addEventListener('pause',function(){window.demo.pause();},true);"
				+ "v.addEventListener('timeupdate',function(){window.demo.timeupdate();},true);"
				+ "v.addEventListener('ended',function(){window.demo.ended();},true);";
		
			js = js + "v.play();";
		
			testWeb.loadUrl(js);
	}
	
    private View xCustomView;
    private WebChromeClient.CustomViewCallback     xCustomViewCallback;
    boolean d = true;
	/**
	 * webchromeClient js 交互
	 */  
	class MyVideoWebChromeClient extends WebChromeClient {

		
//		@Override  
//	    public void onShowCustomView(View view, CustomViewCallback callback) {  
//			
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
//			testWeb.setVisibility(View.GONE);
//	         //如果一个视图已经存在，那么立刻终止并新建一个
//	         if (xCustomView != null) {
//	             callback.onCustomViewHidden();
//	             return;
//	         }            
//	         videoview.addView(view);
//	         xCustomView = view;
//	         xCustomViewCallback = callback;
//	         videoview.setVisibility(View.VISIBLE);
//		}
//		
//		  @Override
//		public void onHideCustomView() {
//			  if (xCustomView == null)//不是全屏播放状态
//	                return;                      
//	            xCustomView.setVisibility(View.GONE);
//	             
//	            // Remove the custom view from its container.
//	            videoview.removeView(xCustomView);
//	            xCustomView = null;
//	            videoview.setVisibility(View.GONE);
//	            xCustomViewCallback.onCustomViewHidden();
//	             
//	            testWeb.setVisibility(View.VISIBLE);
//		}
		
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if(d){
				t.start();
				d = false;
			}
			if(newProgress == 100){
				
			System.out.println("=========onReceivedTitle=newProgress=="+newProgress);
				
			}
//			executeJavaScriptCode();
//			videoJavaScriptCode();
//			pb.setProgress(newProgress);
//			pb.postInvalidate();
		}

		/**
         * @title onReceivedTitle
         * @describe 设置页面标题
         */
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
//			String jscontent = getJavaScriptAsString();
//			view.loadUrl("javascript:" + jscontent + 
//				      "window.onload = function() {first_screen();}");
//			view.loadUrl("javascript:document.addEventListener('DOMContentLoaded', function(){alert(document.addEventListener);};, false);");
//			window.onload = function(){
//				  alert("test1");
//				};
//			view.loadUrl("javascript:window.addEventListener('DOMContentLoaded', function(){prompt('domc:' + new Date().getTime());}, false );");
		}	
		


//		@Override
//		public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult r) {
//			if(message != null){
//					String[] strs = message.split(":");
//					if(2 == strs.length){	
//						if("firstscreen".equals(strs[0])){
//							Toast.makeText(MainActivity.this, Long.valueOf(strs[1].trim())+"", 2000).show();
//						}
//					}
//			}
//			r.confirm(defaultValue);
//			return true;
//		}
		
	}
	
    /**
     * @title executeUrlJavaScriptCode
     * @describe HTML5注入视频源js脚本
     * @param 
     * @return
     * @data 2016-06-22
     */
	private void videoJavaScriptCode() {
		String js = "javascript: var v=document.getElementsByTagName('video')[0];"
				+ "if(v != 'undefined'){window.getUrl.getRealUrl(v.currentSrc,v.src);}";
		
		if(testWeb != null){
			testWeb.loadUrl(js);
		}
		
	}
	Timer realTimeDataTimer;
	TimerTask realTimeDataTimerTask;
	private final int VIDEO_REAL_TIME_DATA_INFO = 4;
    private void realTimeData() {
		realTimeDataTimer = new Timer();
		realTimeDataTimerTask = new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = VIDEO_REAL_TIME_DATA_INFO;
				if (h != null) {
					h.sendMessage(msg);
				}
			}
		};
		realTimeDataTimer.schedule(realTimeDataTimerTask, 0,3000);
	}
	
	private void realTimeDataInfo() {
		videoJavaScriptCode();
	}
    
    /**
     * @title GetUrlJavaScriptInterface
     * @describe HTML5回调类获取视频源
     * @param 
     * @return
     * @data 2016-06-22
     */
	private final class GetUrlJavaScriptInterface {
       
		GetUrlJavaScriptInterface() {}

		@JavascriptInterface
		public void getRealUrl(String videoSrc,String src) { 
			System.out.println("src====videoCurrentSrc===="+videoSrc);
			System.out.println("src====videoSrc===="+src);
//			getRequest(videoSrc);
        }


	}
	
	
	private void getRequest(String videoSrc) {
		if(videoSrc.contains("http://")){
			System.out.println("=========videoSrc======="+videoSrc);
			try {
				URL url = new URL(videoSrc);
				HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.setReadTimeout(5000);
				httpURLConnection.connect();
				InputStream is = httpURLConnection.getInputStream();
				String str = inputStream2String(is);
				System.out.println("=========videoSrc===str===="+str);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	} 
	
	private String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is,"utf-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	private String getJavaScriptAsString() {
//		String js = "function first_screen () {"
//				+ "var imgs = document.getElementsByTagName(\"img\"), fs = +new Date;"
//				+ "var fsItems = [], that = this;"
//				+ "function getOffsetTop(elem) {"
//				+ "var top = 0;"
//				+ "top = window.pageYOffset ? window.pageYOffset : document.documentElement.scrollTop;"
//				+ "try{"
//				+ "top += elem.getBoundingClientRect().top;    "
//				+ "}catch(e){"
//				+ "}finally{"
//				+ "return top;"
//				+ "}"
//				+ "}"
//				+ "var loadEvent = function() {"
//				+ "if (this.removeEventListener) {"
//				+ "this.removeEventListener(\"load\", loadEvent, false);"
//						+ "}"
//						+ "fsItems.push({"
//						+ "img : this,"
//						+ "time : +new Date"
//						+ "});"
//						+ "}"
//						+ "for (var i = 0; i < imgs.length; i++) {"
//						+ "(function() {"
//						+ "var img = imgs[i];"
//						+ "if (img.addEventListener) {"
//						+ "!img.complete && img.addEventListener(\"load\", loadEvent, false);"
//								+ "} else if (img.attachEvent) {"
//								+ "img.attachEvent(\"onreadystatechange\", function() {"
//								+ "if (img.readyState == \"complete\") {"
//								+ "loadEvent.call(img, loadEvent);"
//								+ "}"
//								+ "});"
//								+ "}"
//								+ "});"
//								+ "}"
//								+ "function firstscreen_time() {"
//								+ "var sh = document.documentElement.clientHeight;"
//								+ "for (var i = 0; i < fsItems.length; i++) {"
//								+ "var item = fsItems[i], img = item['img'], time = item['time'], top = getOffsetTop(img);"
//								+ "if (top > 0 && top < sh) {"
//								+ "fs = time > fs ? time : fs;"
//								+ "}"
//								+ "}"
//								+ "return fs;"
//								+ "}"
//								+ "window.addEventListener('load', function() {"
//								+ "prompt('firstscreen:' + firstscreen_time());"
//								+ "});"
//								+ "}";
//		String js = "function first_screen () {"
//				
//				+ "var imgs = document.getElementsByTagName('img'), fs = +new Date;"
//				+ "var fsItems = [], that = this;"
//				+ "function getOffsetTop(elem) {"
//				+ "var top = 0;"
//				+ "top = window.pageYOffset ? window.pageYOffset : document.documentElement.scrollTop;"
//				+ "try{"
//				+ "top += elem.getBoundingClientRect().top;    "
//				+ "}catch(e){"
//				+ "}finally{"
//				+ "return top;"
//				+ "}"
//				+ "}"
//				+ "var loadEvent = function() {"
//				+ "if (this.removeEventListener) {"
//				+ "this.removeEventListener(\"load\", loadEvent, false);"
//						+ "}"
//						+ "fsItems.push({img : this,time : +new Date});"
//						+ "}"
//						+ "for (var i = 0; i < imgs.length; i++) {"
//						+ "(function() {"
//						+ "var img = imgs[i];"
//						+ "if (img.addEventListener) {"
//						+ "!img.complete && img.addEventListener('load', loadEvent, false);"
//								+ "} else if (img.attachEvent) {"
//								+ "img.attachEvent(\"onreadystatechange\", function() {"
//								+ "if (img.readyState == 'complete') {"
//								+ "loadEvent.call(img, loadEvent);"
//								+ "}"
//								+ "});"
//								+ "}"
//								+ "});"
//								+ "}"
//				
//				+ "};"
//				+ "function firstscreen_time() {"
//				+ "var sh = document.documentElement.clientHeight;"
//				+ "for (var i = 0; i < fsItems.length; i++) {"
//				+ "var item = fsItems[i], img = item['img'], time = item['time'], top = getOffsetTop(img);"
//				+ "if (top > 0 && top < sh) {"
//				+ "fs = time > fs ? time : fs;"
//				+ "}"
//				+ "}"
//				+ "return fs;"
//				+ "}"
//				+ "window.addEventListener('load', function() {"
//				+ "prompt('firstscreen:' + firstscreen_time());"
//				+ "});";
		String wholeJS = null;
//		try {
//			InputStream in = getAssets().open("load.js");
//			byte buff[] = new byte[1024];
//			ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
//			FileOutputStream out = null;
//			do {
//			       int numread = in.read(buff);
//			       if (numread <= 0) {
//			         break;
//			         }
//			        fromFile.write(buff, 0, numread);
//			     } while (true);
//			wholeJS = fromFile.toString();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		wholeJS = "function first_screen () {"
				
//                + "var imgs = document.getElementsByTagName('img'), fs = +new Date;"
//                
//                + "var fsItems = [];"
//				+ "var loadEvent = function() {"
//				+ "fsItems.push({"
//				+ "time : +new Date"
//				+ "});"
//				+ "}"
//				
//                + "for (var i = 0; i < imgs.length; i++) {"
//				+ "var img = imgs[i];"
//				+ "img.attachEvent('onreadystatechange', function() {"
//				+ "if (img.complete) {"
//				+ "fsItems.push("
//				+ "+new Date"
//				+ ");"
//				+ "loadEvent.call(img, loadEvent);"
//                + "alert(img);"
//                
//				+ "}"
//				+ "});"	
//				+ "}"
//				
//				+ "function firstscreen_time() {"
//				+ "var sh = document.documentElement.clientHeight;"
//				+ "for (var i = 0; i < fsItems.length; i++) {"
//				+ "var item = fsItems[i], time = item['time'];"
//				+ "fs = item"
//				+ "}"
//				+ "return fs;"
//				+ "}"
//				+ "window.addEventListener('load', function() {"
//				+ "prompt('firstscreen:' + firstscreen_time());"
//				+ "});"

				+ "var performance = window.performance;"
				+ "if ( ! performance ) {"
				+ "alert ( '你的浏览器不支持 performance 接口' ) ;"
				+ "return ;"
				+ "}"
				+ "var t = performance . timing ;"
				+ "alert(t.domContentLoadedEventEnd);"
				+ "}";
		
		
		return wholeJS;
	}
	
	
	
	private void getRealUrl(String videoSrc) {
//		if(videoSrc.contains("m3u8")){
		    String str = "";	
		    try {
				URL url = new URL(videoSrc);
				HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setConnectTimeout(5000);
				httpURLConnection.setReadTimeout(5000);
				httpURLConnection.connect();
				InputStream is = httpURLConnection.getInputStream();
				str = inputStream2String(is);
				if(httpURLConnection.getResponseCode() == 200){
					
//					InputStream is = httpURLConnection.getInputStream();
					str = inputStream2String(is);
				}else if(httpURLConnection.getResponseCode() == 302){
					
//					InputStream is = httpURLConnection.getInputStream();
					str = inputStream2String(is);
					getRequest(str);
				}else{
					str = httpURLConnection.getResponseCode() + "";
				}
				
			} catch (Exception e) {
//				FileUtil.WriteFile(VideoTestSettings.taskItemLogPath, ".video.log", "==获取含有m3u8的url通过get请求失败==="+e.getMessage()+","+"\r\n", true,VideoTestSettings.taskItemLogFileName);
				e.printStackTrace();
			}
			
//		}
	} 
	
}
