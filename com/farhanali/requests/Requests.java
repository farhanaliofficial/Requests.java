package com.farhanali.requests;

/*
Author => Farhan Ali
GitHub => https://github.com/farhanaliofficial/Requests.java
Created Date => 07/10/2023
*/

import java.net.URL;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
import java.io.PrintWriter;
import java.util.Map;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Requests{
	private int HTTPS_PORT = 443;
	private int HTTP_PORT = 80;
	private String HTTP = "HTTP/1.1";
	private String HTTP_POST = "POST";
	private String HTTP_GET = "GET";
	private String DEFAULT_PATH = "/";
	private String VERSION = "1.0.0";
	private String AUTHOR = "Farhan Ali";
	private String GITHUB = "https://github.com/farhanaliofficial/Requests.java";
	
	public RequestsResponse request(String method, String url, Map<String, String> headers, Map<String, String> data) throws Exception{
		URL parsedUrl = new URL(url);
		String host = parsedUrl.getHost();
		String path = parsedUrl.getPath().isEmpty() ? this.DEFAULT_PATH : parsedUrl.getPath();

		boolean isConHead = false;
		boolean isConType = false;

		int port = url.startsWith("https") ? this.HTTPS_PORT : this.HTTP_PORT;

		Socket sock;
		if(port == this.HTTPS_PORT){
			SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sock = ssf.createSocket(host, port);
		}else{
			sock = new Socket(host, port);
		}

		PrintWriter out = new PrintWriter(sock.getOutputStream());
		out.printf("%s %s %s\r\n", method, path, this.HTTP);
		out.printf("Host: %s\r\n", host);
		out.printf("Accept: */*\r\n");

		for(Map.Entry<String, String> head : headers.entrySet()){
			if(head.getKey().toLowerCase().equals("connection"))
				isConHead = true;
			if(head.getKey().toLowerCase().equals("content-type"))
				isConType = true;
			out.printf("%s: %s\r\n", head.getKey(), head.getValue());
		}
		if(!isConHead)
			out.printf("Connection: close\r\n");
		if(method.equals(this.HTTP_POST) && !data.isEmpty()){
			StringBuilder postData = new StringBuilder();
			for(Map.Entry<String, String> dat : data.entrySet()){
				if(postData.length() != 0)
					postData.append("&");

				postData.append(URLEncoder.encode(dat.getKey(), "UTF-8"));
				postData.append("=");
				postData.append(URLEncoder.encode(dat.getValue(), "UTF-8"));
			}
			out.printf("Content-Length: %s\r\n", postData.toString().getBytes().length);
			if(!isConType)
				out.printf("Content-Type: application/x-www-form-urlencoded\r\n");
			out.printf("\r\n");
			out.printf(postData.toString());
		}else{
			out.printf("\r\n");
		}

		out.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String res = "", line;

		while((line = in.readLine()) != null){
			res += line+"\n";
		}

		in.close();
		out.close();
		sock.close();
		RequestsResponseParser parser = new RequestsResponseParser(res);
		RequestsResponse resp = parser.parse();
		return resp;
	}
	public RequestsResponse get(String url, Map<String, String> headers) throws Exception{
		Map<String, String> data = new HashMap<>();
		return request(this.HTTP_GET, url, headers, data);
	}
	public RequestsResponse get(String url) throws Exception{
		Map<String, String> data = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		return request(this.HTTP_GET, url, headers, data);
	}
	public RequestsResponse post(String url, Map<String, String> headers, Map<String, String> data) throws Exception{
		return request(this.HTTP_POST, url, headers, data);
	}
}
