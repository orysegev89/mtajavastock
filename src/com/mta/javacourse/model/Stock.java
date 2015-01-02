package com.mta.javacourse.model;

import java.util.Date;

/**
 * class that handles stock members
 * @author orysegev
 * 
 */
public class Stock {
	
	protected String _symbol;
	protected float _ask;
	protected float _bid;
	protected java.util.Date _date;
	
	public Stock(){
		
	}
	/**
	 * Constructor
	 * @param symbol
	 * @param ask
	 * @param bid
	 * @param date
	 */
	public Stock(String symbol,float ask, float bid,java.util.Date date ){
		this._symbol= symbol;
		this._ask = ask;
		this._bid = bid;
		this._date = date;
	}
	/**
	 * copy constructor
	 * @param instance
	 */
	public Stock(Stock instance){
		this(instance._symbol,instance._ask,instance._bid,new Date(instance._date.getTime()));
	}
	
	
	public String getSymbol() {
		return _symbol;
	}
	
	public void setSymbol(String symbol) {
		this._symbol = symbol;
	}
	
	public float getAsk() {
		return _ask;
	}
	
	public void setAsk(float ask) {
		this._ask = ask;
	}
	
	public float getBid() {
		return _bid;
	}
	

	public void setBid(float bid) {
		this._bid = bid;		
	}
	
	public java.util.Date getDate() {
		return _date;
	}
	
	public void setDate(java.util.Date date) {
		this._date = date;
	}
	
	 public String getHtmlDescription(){
		 String ret = "<br>Stock symbol: " + _symbol + "<br>Ask: "+ _ask + "<br>Bid: " + _bid + "<br>Date: " + getDate();
		 
		 return ret;
		 
	 }
	 

}
