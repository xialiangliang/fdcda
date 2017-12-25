package com.keyou.fdcda.home.face;

import org.json.JSONObject;

public class imageUpLoader{
	public int responseCode=0;
	public String ip = "";
	public String url = "";
	public String delUrl = "";
	public String requestData="";
	public String response="";
	public String Cookie="";
	public String sessionId="";
	public String yt_cluster_id="DEFAULT";
	public JSONObject header=new JSONObject();
	//初始化ip和url
	public imageUpLoader(String fpIp){
		ip = fpIp;
		url = String.format("http://%s:11180", ip);
	}
	//设置sessionId
	public void setSessionId(String session_id) throws Exception{
		Cookie=String.format("session_id=%s;yt_cluster_id=%s",session_id,yt_cluster_id);
		header.put("Cookie", Cookie);
	}
	//设置post内容
	public void setRequestData(JSONObject data){
		requestData=data.toString();
	}
	public void setRequestData(String data){
		requestData=data;
	}
	//设置header内容
	public void setHeader(JSONObject setHeader){
		header=setHeader;
	}
	public void setHeader(String setHeader) throws Exception{
		header=new JSONObject(setHeader);
	}
	public void upLoadSyn() throws Exception{
		String synUrl=url+"/business/api/repository/picture/synchronized";
		httpRequest upLoad = new httpRequest();
		upLoad.setUrl(synUrl);
		upLoad.setReqdata(requestData);
		upLoad.setHeader(header);
		upLoad.post();
		responseCode=upLoad.getResponseCode();
		response=upLoad.getResponse();
	}
}