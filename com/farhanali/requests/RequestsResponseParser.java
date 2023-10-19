package com.farhanali.requests;

/*
Author => Farhan Ali
GitHub => https://github.com/farhanaliofficial/Requests.java
Created Date => 07/10/2023
Last Update => 19/10/2023
*/

import java.util.Map;
import java.util.HashMap;

public class RequestsResponseParser{
	public String response;
	private String oTerminator = "\\r?\\n\\r?\\n";
	private String lTerminator = "\\r?\\n";
	
	public RequestsResponseParser(String response){
		this.response = response;
	}
	public RequestsResponse parse(){
		RequestsResponse httpResponse = new RequestsResponse();
		String[] parts = response.split(oTerminator);
		if(parts.length > 1){
			httpResponse.setContent(parts[1].trim());
		}

		String[] statusLine = parts[0].split(Requests.TERMINATOR)[0].split(" ");
		if(statusLine.length >= 2){
			try{
				httpResponse.setStatusCode(Integer.parseInt(statusLine[1]));
			}catch(Exception e){
				httpResponse.setStatusCode(0);
			}
		}

		String[] headerLines = parts[0].split(lTerminator);
		Map<String, String> headers = new HashMap<>();
		for(int i=1; i<headerLines.length; i++){
			String[] headerParts = headerLines[i].split(": ", 2);
			if(headerParts.length == 2){
				headers.put(headerParts[0], headerParts[1]);
			}
		}
		httpResponse.setHeaders(headers);

		return httpResponse;
	}
}