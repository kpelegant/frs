package com.kp.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.kp.util.file.IOUtil;



/**
 * Properties配置文件读取
 * @author Mr Wang
 *
 */
public class Proper {
	
	//配置文件
	private String properAddress;
	
	public Proper(String properAddress){
		this.properAddress = properAddress;
	}

	/**
	 * 根据key获取配置文件中的value
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getProperty(String key) throws IOException{
		Properties properties = loadProperties();
		return properties.getProperty(key);
	}
	
	/**
	 * 加载Properties文件
	 * @return
	 * @throws IOException 
	 */
	private Properties loadProperties() throws IOException{
		FileInputStream in = new FileInputStream(properAddress);
		Properties properties = new Properties();
		properties.load(in);
		IOUtil.close(in);
		return properties;
	}
}
