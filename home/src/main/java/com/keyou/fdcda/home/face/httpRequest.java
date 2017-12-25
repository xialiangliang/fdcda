package com.keyou.fdcda.home.face;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.json.*;
import sun.misc.BASE64Encoder;

public class httpRequest{
	private int responseCode;
	private String response;
	private String url;
	private String requestData;
	private JSONObject header=new JSONObject();
	private HttpURLConnection httpUrl = null;
	//设置url
	public void setUrl(String toUrl){
		url=toUrl;
	}
	//设置post的data
	public void setReqdata(String sendRequestData){
		requestData=sendRequestData;
	}
	public void setReqdata(JSONObject sendRequestData){
		requestData=sendRequestData.toString();
	}
	//设置header
	public void setHeader(JSONObject toHeader){
		header=toHeader;
	}
	//发送post请求
	public void post() throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		//添加header
		
		@SuppressWarnings("unchecked")
		Iterator<String> it = header.keys();
		while (it.hasNext()){
			String key=it.next();
			con.setRequestProperty(key, header.getString(key) );
		}
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestData);
		wr.flush();
		wr.close();
		//请求结果
		responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuf = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			responseBuf.append(inputLine);
		}
		in.close();
		//打印输出
		response=responseBuf.toString();
		System.out.println(response);
	}
	//发送get请求
	public void get() throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		//添加header
		@SuppressWarnings("unchecked")
		Iterator<String> it = header.keys();
		while (it.hasNext()){
			String key=it.next();
			con.setRequestProperty(key, header.getString(key) );
		}
		//请求结果
		responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuf = new StringBuffer();
		while((inputLine = in.readLine())!= null){
			responseBuf.append(inputLine);
		}
		in.close();
		//打印输出
		response=responseBuf.toString();
		System.out.println(response);	
	}
	public void delete() throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("DELETE");
		//添加header
		@SuppressWarnings("unchecked")
		Iterator<String> it = header.keys();
		while (it.hasNext()){
			String key=it.next();
			con.setRequestProperty(key, header.getString(key) );
		}
		//请求结果
		responseCode = con.getResponseCode();
		System.out.println("\nSending 'DELETE' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuf = new StringBuffer();
		
		while((inputLine = in.readLine())!= null){
			responseBuf.append(inputLine);
		}
		in.close();
		//打印输出
		response=responseBuf.toString();
		System.out.println(response);	
	}
	public void put() throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("PUT");
		//添加header
		@SuppressWarnings("unchecked")
		Iterator<String> it = header.keys();
		while (it.hasNext()){
			String key=it.next();
			con.setRequestProperty(key, header.getString(key) );
		}
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(requestData);
		wr.flush();
		wr.close();
		//请求结果
		responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuf = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null) {
			responseBuf.append(inputLine);
		}
		in.close();
		//打印输出
		response=responseBuf.toString();
		System.out.println("request is : "+requestData);
		System.out.println(response);	
	}
	public void postFile(String path) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		@SuppressWarnings("unchecked")
		Iterator<String> it = header.keys();
		while (it.hasNext()){
			String key=it.next();
			con.setRequestProperty(key, header.getString(key) );
		}
		con.setDoOutput(true);
		InputStream in = null;
		in = new FileInputStream(path);  
		 DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		byte[] data = null;
        data = new byte[in.available()];
        int read = 0;
		while ((read = in.read(data)) != -1) {
			wr.write(data, 0, read);
		}
        in.close();
		wr.flush();
		wr.close();
		responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		BufferedReader resIn = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer responseBuf = new StringBuffer();
		
		while ((inputLine = resIn.readLine()) != null) {
			responseBuf.append(inputLine);
		}
		resIn.close();
		//打印输出
		response=responseBuf.toString();
		System.out.println(response);
	}
	public void getFile(String path) throws Exception{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		//添加header
		@SuppressWarnings("unchecked")
		Iterator<String> it = header.keys();
		while (it.hasNext()){
			String key=it.next();
			con.setRequestProperty(key, header.getString(key) );
		}
		//请求结果
		responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		InputStream in = con.getInputStream();
		byte[] data=null;
		data = new byte[in.available()];	
		FileOutputStream out = new FileOutputStream(new File(path));
		int read = 0;
		while ((read = in.read(data)) != -1) {
			out.write(data, 0, read);
		}
		in.close();
		out.flush();
		out.close();
	}
	/*public InputStream saveToFile(String destUrl) {
		URL url = null;
		InputStream in = null;
		try {
			url = new URL(destUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			in = httpUrl.getInputStream();
			return in;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	public void closeHttpConn() {
		httpUrl.disconnect();
	}

	// 读取输入流,转换为Base64字符串
	public String GetImageStrByInPut(InputStream input) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			data = new byte[input.available()];
			input.read(data);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}