package com.test.test_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceGetRemoteurlThread extends Thread {

	private String remoteUrl;
	private int requestTimes = 1;
	private ServiceGetRemoteurlInterface uiInterface;
	private String remoteDownlaodurlString = "failed";
	private static final String STATUES = "status";
	private static final String URL = "url";

	private static final String TAG = ServiceGetRemoteurlThread.class.getName();

	public ServiceGetRemoteurlThread(String remoteUrl, ServiceGetRemoteurlInterface uiInterface) {
		this.remoteUrl = remoteUrl;
		this.uiInterface = uiInterface;
	}

	@Override
	public void run() {

		downloadUrl();
	}

	private void downloadUrl() {

		JSONObject urlJsonObject = getRemoteDownloadUrl();

		if (urlJsonObject == null) {
			// 网络异常情况获取失败

			while (urlJsonObject == null && requestTimes < 3) {
				urlJsonObject = getRemoteDownloadUrl();
				requestTimes++;
			}

			if (urlJsonObject == null) {
				// 在网络异常的情况下获取失败，通知前台这个地址获取失败
				remoteDownlaodurlString = "failed";
				notifyResult(remoteDownlaodurlString);

			} else {
				// 获取成功了
				requestTimes = 1; // 重置为1，防止再次请求导致次数问题
				parseJsonInfo(urlJsonObject);
			}
		} else {
			parseJsonInfo(urlJsonObject);

		}

	}

	private JSONObject getRemoteDownloadUrl() {
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			URL u = new URL(remoteUrl);
			System.setProperty("http.keepAlive", "false");
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);
			conn.connect();

			is = conn.getInputStream();
			String result = inputStream2String(is);
			JSONObject urlObject = new JSONObject(result);
			return urlObject;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				is = null;
			}
			if(conn != null){
				conn.disconnect();
				conn = null;
			}
		}

		return null;

	}

	private void parseJsonInfo(JSONObject urlJsonObject) {
		// 从服务器端获取到了返回的信息。
		// 解析json内容,看json里面的状态码。如果是成功状态码，取后面的内容；如果是失败状态码，重新获取内容

		try {
			int status = urlJsonObject.getInt(STATUES);
			if (status == 1) {
				// 失败
				while (status == 0 && requestTimes <= 3) {
					urlJsonObject = getRemoteDownloadUrl();
					status = urlJsonObject.getInt("status");
					requestTimes++;
				}

				if (status == 1) {
					// 失败
					remoteDownlaodurlString = "failed";
					notifyResult(remoteDownlaodurlString);
				} else {
					remoteDownlaodurlString = urlJsonObject.getString(URL);
					// 通知前台获取成功了
					notifyResult(remoteDownlaodurlString);
				}

			} else {
				// 成功
				remoteDownlaodurlString = urlJsonObject.getString(URL);
				// 通知前台获取成功了
				notifyResult(remoteDownlaodurlString);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			notifyResult(remoteDownlaodurlString);
		}
	}

	private String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	private void notifyResult(String result) {
		if (uiInterface != null) {
			uiInterface.remoteUrlDownResult(result);
		}

	}
	

}
