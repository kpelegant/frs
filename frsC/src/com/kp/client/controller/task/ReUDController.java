package com.kp.client.controller.task;

import com.kp.entity.trans.Apply;

/**
 * 上传下载
 * @author Mr Wang
 *
 */
public interface ReUDController {
	
	public void run(UDCSTransmissionBasic basic,
			Apply apply);
	
	public void refreshFolder();
	
	public void updateFileName(UDCSTransmissionBasic basic, String name);
}
