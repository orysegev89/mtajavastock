package com.mta.javacourse.model;

import java.util.ArrayList;
import java.util.Date;

import sun.java2d.cmm.ProfileActivator;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
/**
 * to handle the data model - stocks
 * @category portfolio class
 * @author orysegev
 *
 */
public class Portfolio {
	
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private String _title = "portfolio1";
	private Stock [] _stocks;
	private StockStatus[] _stocksStatus;
	private int _portfolioSize = 0;
	
	
	public Portfolio(){
		_stocks = new Stock[MAX_PORTFOLIO_SIZE];
		_stocksStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
	}
	/**
	 * copy constructor
	 * @param stock
	 * @param stockStatus
	 * @param portfolioSize
	 */
	public Portfolio(Stock[] stock, StockStatus[] stockStatus,int portfolioSize){
		this();
		for (int i = 0; i < portfolioSize; i++) {
			this.addStock(new Stock (stock[i]));
		}
	}
		/**
		 * copy constructor
		 * @param instance
		 */
	public Portfolio(Portfolio instance){
			this(instance._stocks,instance._stocksStatus,instance._portfolioSize);
	}
	
	
	public String getTitle() {
		return _title;
	}

	public void setTitle(String _title) {
		this._title = _title;
	}

	public void addStock(Stock stock){
		_stocks[_portfolioSize] = stock;
		_portfolioSize++;
	}
	public void removeFirstStock(){
		for (int i = 0; i <_portfolioSize-1; i++) {
			_stocks[i] = _stocks[i+1];
		}
		_portfolioSize--;
	}

	public Stock[] getStock(){
		return _stocks;
	}
	
	public String getHtmlString(){
		String str;
		str = "<h1>"+ _title +"</h1>";
		for(int i=0;i < _portfolioSize; i++){
			str += _stocks[i].getHtmlDescription();
		}
		return str;
	}
	
	private class StockStatus{
		String synbol;
		float currentBid, currentAsk;
		Date date;
		int recommendation;
		int stockQuantity;
		
		private static final int DO_NOTHING = 0;
		private static final int BUY = 1;
		private static final int SELL = 2;
		
		
		
	}
}
