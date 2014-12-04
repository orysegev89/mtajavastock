package com.mta.javacourse.model;

import java.util.Date;

import com.mta.javacourse.Stock;

public class Portfolio {
	
	private static final int MAX_PORTFOLIO_SIZE = 5;
	private String title = "portfolioTitle";
	private Stock[] stocks;
	private StockStatus[] stocksStatus;
	private int portfolioSize = 0;
	
	
	public Portfolio(){
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		stocksStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
	}
	
	public void addStock(Stock stock){
		stocks[portfolioSize] = stock;
		portfolioSize++;
	}

	public Stock[] getStock(){
		return stocks;
	}
	
	public String getHtmlString(){
		String str;
		str = "<h1>"+ title +"</h1>";
		for(int i=0;i < portfolioSize; i++){
			str += stocks[i].getHtmlDescription();
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
