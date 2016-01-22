package com.ksy.djd.model.system;

import java.io.File;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private File display;
    private String sex;
	/**
	 * @return the display
	 */
	public File getDisplay(){
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(File display){
		this.display = display;
	}
	/**
	 * @return the sex
	 */
	public String getSex(){
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex){
		this.sex = sex;
	}

    
	
}
