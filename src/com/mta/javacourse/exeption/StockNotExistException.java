package com.mta.javacourse.exeption;

public class StockNotExistException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public StockNotExistException(String symbol) {
		super("The stock "+ symbol + " does not exist in the portfolio!");
	}
}
