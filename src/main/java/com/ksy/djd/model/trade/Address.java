package com.ksy.djd.model.trade;

import cn.bmob.v3.BmobObject;

public class Address extends BmobObject{
  private String username;
  private String mobilePhoneNumber;
  private String address;
/**
 * @return the username
 */
public String getUsername(){
	return username;
}
/**
 * @param username the username to set
 */
public void setUsername(String username){
	this.username = username;
}
/**
 * @return the mobilePhoneNumber
 */
public String getMobilePhoneNumber(){
	return mobilePhoneNumber;
}
/**
 * @param mobilePhoneNumber the mobilePhoneNumber to set
 */
public void setMobilePhoneNumber(String mobilePhoneNumber){
	this.mobilePhoneNumber = mobilePhoneNumber;
}
/**
 * @return the address
 */
public String getAddress(){
	return address;
}
/**
 * @param address the address to set
 */
public void setAddress(String address){
	this.address = address;
}
  
}
