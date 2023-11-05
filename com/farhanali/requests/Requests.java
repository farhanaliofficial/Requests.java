package com.farhanali.requests;

/*
Author => Farhan Ali
GitHub => https://github.com/farhanaliofficial/Requests.java
Created Date => 07/10/2023
Last Update => 05/11/2023
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
	private String VERSION = "1.0.3";
	private String AUTHOR = "Farhan Ali";
	private String GITHUB = "https://github.com/farhanaliofficial/Requests.java";
	public static String TERMINATOR = "\r\n";
	
	public RequestsResponse request(String method, String url, Map<String, String> headers, Map<String, String> data) throws Exception{
		URL parsedUrl = new URL(url);
		String host = parsedUrl.getHost();
		String query = parsedUrl.getQuery();
		String path = parsedUrl.getPath().isEmpty() ? this.DEFAULT_PATH : parsedUrl.getPath();
		path += query != null ? "?" + query : "";
		int port = url.startsWith("https") ? this.HTTPS_PORT : this.HTTP_PORT;
		
		boolean isConType = false;

		Socket sock;
		if(port == this.HTTPS_PORT){
			SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sock = ssf.createSocket(host, port);
		}else{
			sock = new Socket(host, port);
		}

		PrintWriter out = new PrintWriter(sock.getOutputStream());
		out.printf("%s %s %s%s", method, path, this.HTTP, this.TERMINATOR);
		
		headers.put("host", host);
		
		for(Map.Entry<String, String> head : Utils.getCombinedHeaders(headers).entrySet()){
			if(head.getKey().toLowerCase().equals("content-type"))
				isConType = true;
			out.printf("%s: %s%s", head.getKey(), head.getValue(), this.TERMINATOR);
		}
		
		if(method.equals(this.HTTP_POST) && !data.isEmpty()){
			String postData = Utils.buildPostData(data);
			out.printf("Content-Length: %s%s", postData.getBytes().length, this.TERMINATOR);
			if(!isConType)
				out.printf("Content-Type: application/x-www-form-urlencoded%s", this.TERMINATOR);
			out.printf("%s%s", this.TERMINATOR, postData);
		}else{
			out.printf(this.TERMINATOR);
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
		return parser.parse();
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
