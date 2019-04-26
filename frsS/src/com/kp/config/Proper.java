package com.kp.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
		String value = properties.getProperty(key);
		if(value == null) throw new IOException("Proper.getProperty IOException");
		return value;
	}
	
	/**
	 * 设置value
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setProperty(String key, String value) throws IOException{
		Properties properties = loadProperties();
		properties.setProperty(key, value);
		saveProperties(properties);
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
	
	/**
	 * 保存Properties文件
	 * @return
	 * @throws IOException 
	 */
	private Properties saveProperties(Properties properties) throws IOException{
		FileOutputStream out = new FileOutputStream(properAddress, false);
		properties.store(out, "");
		IOUtil.close(out);
		return properties;
	}
}
