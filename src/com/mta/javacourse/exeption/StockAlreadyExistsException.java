package com.mta.javacourse.exeption;

public class StockAlreadyExistsException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public StockAlreadyExistsException(String symbol) {
		super("The stock" + symbol + "already exists!");
	}
}
