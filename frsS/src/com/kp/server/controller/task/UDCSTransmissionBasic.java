package com.kp.server.controller.task;

import com.kp.server.controller.task.execute.ExecutantBasic;

/**
 * 上传、下载基础
 * @author Mr Wang
 *
 */
public class UDCSTransmissionBasic extends CSTransmissionBasic {

	protected ExecutantBasic[] execute;
	
	public UDCSTransmissionBasic(CSTransmissionBasic basic){
		super(basic);
	}
	
	/**
	 * 暂停
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void pause(){
		super.pause();
		if(execute != null){
			for(ExecutantBasic e : execute){
				if(e != null){
					e.suspend();
				}
			}
		}
	}
	
	/**
	 * 继续
	 */
	@Override
	@SuppressWarnings("deprecation")
	public void goon(){
		super.goon();
		if(execute != null){
			for(ExecutantBasic e : execute){
				if(e != null){
					e.resume();
				}
			}
		}
	}
	
	@Override
	public void setStop(){
		super.setStop();
		if(execute != null){
			for(ExecutantBasic basic : execute){
				basic.setStop(true);
			}
		}
	}
}
