package com.farhanali.requests;

/*
Author => Farhan Ali
GitHub => https://github.com/farhanaliofficial/Requests.java
Created Date => 19/10/2023
Last Update => 19/10/2023
*/

import java.util.Map;
import java.util.HashMap;
import java.net.URLEncoder;

public class Utils{
	public static Map<String, String> getCombinedHeaders(Map<String, String> headers){
		Map<String, String> defaultHeaders = new HashMap<>();
		defaultHeaders.put("accept", "*/*");
		defaultHeaders.put("connection", "close");
		defaultHeaders.put("user-agent", "Requests.java (By Farhan Ali) v0.0.2");
		Map<String, String> newHeaders = new HashMap<>(defaultHeaders);
		
		for(Map.Entry<String, String> item : headers.entrySet()){
			newHeaders.put(item.getKey().toLowerCase(), item.getValue().toLowerCase());
		}
		return newHeaders;
	}
	public static String buildPostData(Map<String, String> data) throws Exception{
		String postData = "";
		for(Map.Entry<String, String> dat : data.entrySet()){
			if(postData.length() != 0)
				postData += "&";
			postData += URLEncoder.encode(dat.getKey(), "UTF-8")+"="+URLEncoder.encode(dat.getValue(), "UTF-8");
		}
		return postData;
	}
}