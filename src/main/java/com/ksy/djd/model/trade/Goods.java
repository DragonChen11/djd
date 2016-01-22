package com.ksy.djd.model.trade;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Goods extends BmobObject{
	private String tradeName;
	private String tradeMessage;
	private double price;
	private String brand;
	private String type;
	private BmobFile picture;
	
	/**
	 * @return the picture
	 */
	public BmobFile getPicture(){
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(BmobFile picture){
		this.picture = picture;
	}
	public Goods(){
		super();
		// TODO Auto-generated constructor stub
	}
	public Goods(String tableName){
		super(tableName);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the tradeName
	 */
	public String getTradeName(){
		return tradeName;
	}
	/**
	 * @param tradeName the tradeName to set
	 */
	public void setTradeName(String tradeName){
		this.tradeName = tradeName;
	}
	/**
	 * @return the tradeMessage
	 */
	public String getTradeMessage(){
		return tradeMessage;
	}
	/**
	 * @param tradeMessage the tradeMessage to set
	 */
	public void setTradeMessage(String tradeMessage){
		this.tradeMessage = tradeMessage;
	}
	
	/**
	 * @return the price
	 */
	public double getPrice(){
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price){
		this.price = price;
	}
	/**
	 * @return the brand
	 */
	public String getBrand(){
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand){
		this.brand = brand;
	}
	/**
	 * @return the type
	 */
	public String getType(){
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type){
		this.type = type;
	}
	
	

}
