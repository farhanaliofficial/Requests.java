package com.farhanali.requests;

/*
Author => Farhan Ali
GitHub => https://github.com/farhanaliofficial/Requests.java
Created Date => 07/10/2023
*/

import java.util.Map;

public class RequestsResponse{
	String content;
	int status_code;
	Map<String, String> headers;

	public RequestsResponse(String content, int status_code, Map<String, String> headers){
		this.content = content;
		this.status_code = status_code;
		this.headers = headers;
	}
	public RequestsResponse(){

	}
	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setStatusCode(int status_code){
		this.status_code = status_code;
	}

	public int getStatusCode(){
		return status_code;
	}

	public void setHeaders(Map<String, String> headers){
		this.headers = headers;
	}

	public Map<String, String> getHeaders(){
		return headers;
	}
}
