package com.ksy.djd.model.trade;

import cn.bmob.v3.BmobObject;

public class Order extends BmobObject {
	private String tradeName;
	private Number amount;
	private Number price;
	private Number total;
	private String orderId;
	public Order(){
		super();
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
	 * @return the amount
	 */
	public Number getAmount(){
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Number amount){
		this.amount = amount;
	}
	/**
	 * @return the price
	 */
	public Number getPrice(){
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(Number price){
		this.price = price;
	}
	/**
	 * @return the total
	 */
	public Number getTotal(){
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Number total){
		this.total = total;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId(){
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	

}
