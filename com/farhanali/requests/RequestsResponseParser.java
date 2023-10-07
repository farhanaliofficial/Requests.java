package com.farhanali.requests;

/*
Author => Farhan Ali
GitHub => https://github.com/farhanaliofficial/Requests.java
Created Date => 07/10/2023
*/

import java.util.Map;
import java.util.HashMap;

public class RequestsResponseParser{
	String response;
	public RequestsResponseParser(String response){
		this.response = response;
	}
	public RequestsResponse parse(){
		RequestsResponse httpResponse = new RequestsResponse();

		String[] parts = response.split("\\r?\\n\\r?\\n");
		if(parts.length > 1){
			httpResponse.setContent(parts[1].trim());
		}

		String[] statusLine = parts[0].split("\r\n")[0].split(" ");
		if(statusLine.length >= 2){
			try{
				httpResponse.setStatusCode(Integer.parseInt(statusLine[1]));
			}catch(NumberFormatException e){
				httpResponse.setStatusCode(0);
			}
		}

		String[] headerLines = parts[0].split("\\r?\\n");
		Map<String, String> headers = new HashMap<>();
		for (int i = 1; i < headerLines.length; i++) {
			String[] headerParts = headerLines[i].split(": ", 2);
			if (headerParts.length == 2) {
				String key = headerParts[0];
				String value = headerParts[1];
				headers.put(key, value);
			}
		}
		httpResponse.setHeaders(headers);

		return httpResponse;
	}
}
