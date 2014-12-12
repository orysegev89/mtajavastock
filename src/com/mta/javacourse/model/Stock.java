package com.mta.javacourse.model;
/**
 * class that handles stock members
 * @author orysegev
 * 
 */
public class Stock {
	
	private String symbol;
	private float ask;
	private float bid;
	private java.util.Date date;
	
	public Stock(){
		
	}
	/**
	 * constractor
	 * @param symbol
	 * @param ask
	 * @param bid
	 * @param date
	 */
	public Stock(String symbol,Float ask, Float bid,java.util.Date date ){
		this.symbol= symbol;
		this.ask = ask;
		this.bid = bid;
		this.date = date;
	}
	/**
	 * copy constuctor
	 * @param instance
	 */
	public Stock(Stock instance){
		this(instance.symbol,instance.ask,instance.bid, instance.date);
	}
	
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public float getAsk() {
		return ask;
	}
	
	public void setAsk(float ask) {
		this.ask = ask;
	}
	
	public float getBid() {
		return bid;
	}
	

	public void setBid(float bid) {
		this.bid = bid;		
	}
	
	public java.util.Date getDate() {
		return date;
	}
	
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	
	 public String getHtmlDescription(){
		 String ret = "<br>Stock symbol: " + symbol + "<br>Ask: "+ ask + "<br>Bid: " + bid + "<br>Date: " + getDate();
		 
		 return ret;
		 
	 }
	 

}
