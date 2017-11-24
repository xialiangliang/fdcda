package com.keyou.fdcda.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileUtil {
	
	public static void main(String[] args) {
		System.out.println(getFileModifiedTime("d:\\Jvm入门.docx"));
	}
	
	 /** 
     * 读取文件创建时间 
     */  
    public static void getCreateTime(String filePath){  
        String strTime = null;  
        try {  
            Process p = Runtime.getRuntime().exec("cmd /C dir "           
                    + filePath  
                    + "/tc" );  
            InputStream is = p.getInputStream();   
            BufferedReader br = new BufferedReader(new InputStreamReader(is));             
            String line;  
            while((line = br.readLine()) != null){  
                if(filePath.indexOf(line)!=-1){  
                    strTime = line.substring(0,17);  
                    break;  
                }                             
             }   
        } catch (IOException e) {  
            e.printStackTrace();  
        }         
        System.out.println("创建时间    " + strTime);     
        //输出：创建时间   2009-08-17  10:21  
    }  
    
    
	
	/**
	 * 读取文件的最后修改时间  
	 * @param path
	 * @return
	 */
    public static String getFileModifiedTime(String path){
    	try {
    		File f = new File(path);              
    		Calendar cal = Calendar.getInstance();  
    		long time = f.lastModified();  
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
    		cal.setTimeInMillis(time);    
    		return formatter.format(cal.getTime());     
		} catch (Exception e) {
		}
    	return null;
    }  
    
	/**
	 * 读取文件的最后修改时间  
	 * @param path
	 * @return
	 */
    public static String getFileModifiedTime(File f){
    	try {
    		Calendar cal = Calendar.getInstance();  
    		long time = f.lastModified();  
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
    		cal.setTimeInMillis(time);    
    		return formatter.format(cal.getTime());     
		} catch (Exception e) {
		}
    	return null;
    } 
}
