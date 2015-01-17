package com.mta.javacourse.exeption;

public class NotEnoughQuantityException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public NotEnoughQuantityException(String symbol, int quantinty){
		super("The stock " + symbol + " contains less than " + quantinty + "in the portfolio!" );
	}

}
