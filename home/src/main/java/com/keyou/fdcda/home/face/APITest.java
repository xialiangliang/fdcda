package com.keyou.fdcda.home.face;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class APITest {
	public static void main(String[] args) throws Exception {
		JSONObject responseJson = null;
		JSONObject requestJson = null;
		// 请填写您的IP地址
		String ip = "60.191.246.29";
		String imageString = "", pictureUri = "";
		String sessionId = "";
		String faceImageId = "";
		// 登陆
		resourceManager testResource = new resourceManager(ip);
		try {
			String loginData = getFileStr("接口requestData\\登陆.txt");
			testResource.setRequestData(loginData);// 写入文件的json格式data
			testResource.userLogin();// 登陆
			sessionId = testResource.sessionId;// 保存sessionId
			responseJson = new JSONObject(testResource.response);// 保存返回结果
		} catch (Exception e) {
			System.out.println("登陆接口出现问题：" + e);
		}
		if (testResource.responseCode == 200) {
			System.out.println("登陆接口可用。");
			testResource.responseCode = 0;
		} else {
		}
		// 图片base64编码
		String imagePath = "接口requestData\\20171124135709.jpg";
		try {
			imageString = getImageStr(imagePath);
		} catch (Exception e) {
			System.out.println("图片进行编码出现问题：" + e);
		}
		// 导图同步
		imageUpLoader testImageUploader = new imageUpLoader(ip);
		testImageUploader.setSessionId(sessionId);
		try {
			String imageUploadData = getFileStr("接口requestData\\导图.txt");
			requestJson = new JSONObject(imageUploadData);
			requestJson.put("picture_image_content_base64", imageString);
			// requestJson.put("repository_id", repositoryId1);
			testImageUploader.setRequestData(requestJson.toString());
			testImageUploader.upLoadSyn();
			responseJson = new JSONObject(testImageUploader.response);
		} catch (Exception e) {
			System.out.println("同步导图接口出现问题：" + e);
		}
		if (testImageUploader.responseCode == 200) {
			faceImageId = responseJson.getJSONArray("results").getJSONObject(0).getString("face_image_id");
			pictureUri = responseJson.getString("picture_uri");
			System.out.println("同步导图接口可用。");
			testImageUploader.responseCode = 0;
		} else {
		}
		// 人脸检索
		faceManager testFaceManager = new faceManager(ip);
		testFaceManager.setSessionId(sessionId);
		try {
			String faceData = getFileStr("接口requestData\\人像-人脸检索.txt");
			requestJson = new JSONObject(faceData);
			JSONObject faceRetrieval = new JSONObject(requestJson.getJSONObject("retrieval").toString());
			JSONArray repositoryIds = new JSONArray(
					requestJson.getJSONObject("retrieval").getJSONArray("repository_id").toString());
			faceRetrieval.put("face_image_id", faceImageId);
			// repositoryIds.put(1);
			faceRetrieval.put("repository_ids", repositoryIds);
			requestJson.put("retrieval", faceRetrieval);
			requestJson.put("extra_fields", new String[]{"image_row_id"} );
			System.out.println(requestJson.toString());
			testFaceManager.setRequestData(requestJson.toString());
			testFaceManager.showFaceRetrieval();
			responseJson = new JSONObject(testFaceManager.response);
		} catch (Exception e) {
			System.out.println("人脸检索接口出现问题：" + e);
		}
		if (testFaceManager.responseCode == 200) {
			System.out.println("人脸检索接口可用。");
			testFaceManager.responseCode = 0;
		} else {
		}

	}

	public static String getFileStr(String path) throws Exception {
		File filename = new File(path); // 读取以上路径的文件
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
		BufferedReader stringBuf = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
		String line = "";
		StringBuffer fileString = new StringBuffer();
		while ((line = stringBuf.readLine()) != null) {
			fileString.append(line); // 一次读入一行数据
		}
		stringBuf.close();
		return fileString.toString();
	}

	public static String getImageStr(String path) throws Exception {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		in = new FileInputStream(path);
		data = new byte[in.available()];
		in.read(data);
		in.close();
		// 对字节数组Base64编码
		String base64Encode = Base64.getEncoder().encodeToString(data);
		return base64Encode;// 返回Base64编码过的字节数组字符串

	}

}