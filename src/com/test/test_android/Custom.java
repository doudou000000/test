package com.test.test_android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Custom {

	private final int VIDEO_REAL_TEST_URL = 1;
	
	private final int VIDEO_PLAY_TIME_OUT = 3;


	private WebView video_webview;

	private Context context;
	
	private ProgressBar pb;
	
	private TaskThread taskThread;
	
	private String remoteDownloadUrl;

	private boolean isSingleUrlTestFinish;
	
    Timer playTimeOutTimer;
    TimerTask playTimeOutTimerTask;

    
	List<String> upOrDownFileList;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case VIDEO_REAL_TEST_URL:
				initWebView((String) msg.obj);
				break;
			case VIDEO_PLAY_TIME_OUT:
				stopSingleTask();
				break;
			case 2:
				executeJavaScriptCode(true);
				break;
			default:
				break;
			}
		}

	};
	
	public Custom(WebView testWeb, ProgressBar pb, Context context) {
		super();
		this.video_webview = testWeb;
		this.pb = pb;
		this.context = context;
		upOrDownFileList = new ArrayList<String>();
		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("http://m.iqiyi.com/v_19rrnr18nc.html");
//		upOrDownFileList.add("https://m.v.qq.com/cover/n/neezcc6p0ra49ff.html");
//		upOrDownFileList.add("https://m.v.qq.com/cover/n/neezcc6p0ra49ff.html");
//		upOrDownFileList.add("https://m.v.qq.com/cover/n/neezcc6p0ra49ff.html");
//		upOrDownFileList.add("https://m.v.qq.com/cover/n/neezcc6p0ra49ff.html");
//		upOrDownFileList.add("https://m.v.qq.com/cover/n/neezcc6p0ra49ff.html");
		executeTest();
//		initWebView("https://m.v.qq.com/cover/n/neezcc6p0ra49ff.html");
	}

	/**
	 * @Title: exsuceUrl
	 * @Description: TODO
	 * @param 
	 * @return void
	 * @throws
	 */
	private void executeTest() {
		// TODO Auto-generated method stub
		//判断测试线程是否在启动，如果是就关闭
		if(taskThread != null){
			taskThread.interrupt();
			taskThread = null;
		}	
		//开启测试线程  
		taskThread = new TaskThread();
		taskThread.start();	
	}
	
	/**
	 * @ClassName: TaskThread
	 * @Description: TODO 获取远程URL线程
	 * @author chengpf
	 * @date 2016年8月18日 上午11:21:30
	 */
	class TaskThread extends Thread implements ServiceGetRemoteurlInterface {
		
		public void run() {
			
			//根据用例文件中的URL循环获取远程URL
			for (int i = 0; i < upOrDownFileList.size(); i++) {
				eachTimeInitData();
				//用例中的URL
				String urlString = upOrDownFileList.get(i);
				//判断URL是否含有http
				if(!urlString.contains("http://") && !urlString.contains("https://")){
					urlString = "http://" + urlString;
				}
				//判断是否需要从后台取.如果urlString含有getCtpHTTPUrl则后台获取url
				if (!urlString.contains("getCtpHTTPUrl")) {
                    //本地URL 			
					remoteDownloadUrl = urlString;
				} else {	
					// 获取后台地址的操作（执行3次）
					new ServiceGetRemoteurlThread(urlString, this).start();
					// 等待远程地址获取成功
					while (remoteDownloadUrl == null) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 获取失败,提示用户本地址获取失败
					if ("failed".equals(remoteDownloadUrl)) {
						break;
					}
				}				

				//获取真实url地址成功后，发送消息，执行webview加载网页操作
				Message msg = new Message();
				msg.what = VIDEO_REAL_TEST_URL;
				msg.obj = remoteDownloadUrl;
				if (handler != null) {	
					handler.sendMessage(msg);
				}
				//等待单个测试结束
				while (!isSingleUrlTestFinish) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		/**
		* @Title: remoteUrlDownResult
		* @Description: 获取远程URL地址
		* @see com.cmri.browse.test.ServiceGetRemoteurlInterface#remoteUrlDownResult(java.lang.String)
		*/
		@Override
		public void remoteUrlDownResult(String url) {
			//远程地址
			remoteDownloadUrl = url;
		};
    }
	
	/**
	 * @Title: eachTimeInitData
	 * @Description: TODO
	 * @param 
	 * @return void
	 * @throws
	 */
	private void eachTimeInitData() {
		remoteDownloadUrl = null;
		isSingleUrlTestFinish = false;
		isStop = true;
		playTimeout();
	}
	
	
	/**
	 * 
	 * @Title: initWebView
	 * @Description: TODO
	 * @param @param url webview加载URL
	 * @return void
	 * @throws
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView(String url) {
		video_webview.getSettings().setSupportZoom(true);
		video_webview.getSettings().setBuiltInZoomControls(true);
		video_webview.getSettings().setUseWideViewPort(true);
		video_webview.getSettings().setJavaScriptEnabled(true);
		video_webview.getSettings().setDomStorageEnabled(true);   
        //webview自定义js借口
		video_webview.addJavascriptInterface(new DemoJavaScriptInterface(), "demo"); 	
//		//webview自定义js借口
//		video_webview.addJavascriptInterface(new GetUrlJavaScriptInterface(), "getUrl"); 
//		//webview自定义js借口
//		video_webview.addJavascriptInterface(new NoVideoJavaScriptInterface(), "noVideoTag");
		//为webview设置自定义WebChromeClient和WebViewClient
		video_webview.setWebViewClient(new MyVideoWebViewClient());	
		video_webview.setWebChromeClient(new MyVideoWebChromeClient());
//		video_webview.loadUrl(url);
		videoViewLoadUrl(url);
	}

	/**
	 * 
	 * @Title: webviewLoadUrl
	 * @Description: TODO
	 * @param @param url 
	 * @return void
	 * @throws
	 */
	private void videoViewLoadUrl(String url) {
//		if (video_webview != null) {
//			try {
//				video_webview.resumeTimers();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			video_webview.loadUrl(url);
//		}
	}
	
	/**
	 * @title MyWebViewClient
	 * @description 自定义webview
	 * @author chengpf
	 *
	 */
	class MyVideoWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (url.contains("leclient")||url.contains("ifengvideoplayer")||url.contains("letvclient")||url.contains("xunlei")) {
				
				return true;
			}
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			System.out.println("=========onReceivedTitle=onPageStarted=="+url);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			excuteJs();
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

	}
	/**
	 * webchromeClient js 交互
	 */  
	class MyVideoWebChromeClient extends WebChromeClient {

		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			System.out.println("=========onReceivedTitle=newProgress=="+newProgress);
	
			executeJavaScriptCode(true);
			pb.setProgress(newProgress);
			pb.postInvalidate();
		}

		/**
         * @title onReceivedTitle
         * @describe 设置页面标题
         */
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			System.out.println("=========onReceivedTitle=title=="+title);
		}	
	}

    /**
     * @title executeJavaScriptCode
     * @describe HTML5注入js脚本
     * @param 
     * @return
     * @author chengpf
     * @data 2016-06-22
     */
	private void executeJavaScriptCode(boolean isCallBackPlay) {
		String js = "javascript: var v=document.getElementsByTagName('video')[0];"
				+ "v.addEventListener('play',function(){window.demo.play();},true);"
				+ "v.addEventListener('pause',function(){window.demo.pause();},true);"
				+ "v.addEventListener('timeupdate',function(){window.demo.timeupdate();},true);"
				+ "v.addEventListener('ended',function(){window.demo.ended();},true);";
		if(remoteDownloadUrl.contains("qq")){
			js = js + "var s=window.localStorage;if(s){s.clear();}";
	    }
		if(video_webview.getProgress() <= 20){
			js = js + "";
		}else{
			js = js + "v.play();";
		}
		video_webview.loadUrl(js);
	}
	
	   /**
     * @title DemoJavaScriptInterface
     * @describe HTML5回调类
     * @param 
     * @return
     * @author chengpf
     * @data 2016-06-22
     */
	private final class DemoJavaScriptInterface {
       
		DemoJavaScriptInterface() {}
		
	    /**
	     * @title play
	     * @describe 视频播放回调
	     * @param 
	     * @return
	     * @author chengpf
	     * @data 2016-06-22
	     */
		@JavascriptInterface
		public void play() {
			System.out.println("=========onReceivedTitle=play==播放");
		}
		
	    /**
	     * @title pause
	     * @describe 视频暂停回调
	     * @param 
	     * @return
	     * @author chengpf
	     * @data 2016-06-22
	     */
		@JavascriptInterface
		public void pause() {
			System.out.println("=========onReceivedTitle=play==暂停");
		}
		
	    /**
	     * @title timeupdate
	     * @describe 视频播放位置回调
	     * @param 
	     * @return
	     * @author chengpf
	     * @data 2016-06-22
	     */
		@JavascriptInterface
		public synchronized void timeupdate() {
			System.out.println("=========onReceivedTitle=play==卡顿");
		}
		
	    /**
	     * @title ended
	     * @describe 视频停止回调
	     * @param 
	     * @return
	     * @author chengpf
	     * @data 2016-06-22
	     */
		@JavascriptInterface
		public void ended() {
			//视频结束后直接进入下一个视频
			System.out.println("=========onReceivedTitle=play==结束");
			video_webview.post(new Runnable() {
	             @Override
	             public void run() {
	            	 video_webview.onResume();		     
	             }
	         });
		}
	}
	
	/**
     * @Title isPlayTimeout
     * @decripe 视频超时退出
     * @author chengpf
     * @data 2016-06-14 上午11:39
     */
	private void playTimeout() {
		if (video_webview != null) {
			playTimeOutTimer = new Timer();
			playTimeOutTimerTask = new TimerTask() {
				@Override
				public void run() {
					Message msg = new Message();
					msg.what = VIDEO_PLAY_TIME_OUT;
					if (handler != null) {
						handler.sendMessage(msg);
					}
				}
			};
			playTimeOutTimer.schedule(playTimeOutTimerTask, 60 * 1000);
		} 
	}
	
	/**
	 * @Title: stopSingleTask
	 * @Description: 测试能力中应该有中断机制，可以主动/意外终止测试。
	 * @return void
	 * @throws
	 * @author chengpf
	 * @data 2016-06-22
	 */
	public void stopSingleTask() {

		stopVideo();
		closeTimer();
		isSingleUrlTestFinish = true;

	}
	
	/**
	 * @Title: stopVideo
	 * @Description: 停止视频播放
	 * @param
	 * @return void
	 * @throws
	 * @author chengpf
	 * @data 2016-06-22
	 */
	private void stopVideo() {
		// 如果视频正在播放，即可停止
		isStop = false;
		video_webview.post(new Runnable() {
		        @Override
		        public void run() {
		            //视频播放停止
		            video_webview.onPause();
	                video_webview.clearCache(true);
	                video_webview.clearHistory();
	                video_webview.clearFormData();
		    		try {
//		    			video_webview.stopLoading();
//		    			video_webview.pauseTimers();
		    			video_webview.resumeTimers();
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
	   });

	}
	
	/**
	 * 
	* @Title: closeTimer 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @return TODO    返回类型 
	* @throws
	 */
	private void closeTimer() {
		if(playTimeOutTimer != null){
			playTimeOutTimer.cancel();
			playTimeOutTimer = null;
		}
	}

	/**
	 * @Title: isQQUrl
	 * @Description: 在页面加载完成后开始加载js
	 * @return void
	 * @throws
	 * @author chengpf
	 * @data 2016-06-22
	 */
	boolean isStop = true;
    private void excuteJs() {
			new Thread(){
				public void run() {	
					while(isStop){
						try {
							Thread.sleep(1000);							
							Message msg = new Message();
							msg.what = 2;
							if (handler != null) {
								handler.sendMessage(msg);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
			}.start();
	}
	
}
