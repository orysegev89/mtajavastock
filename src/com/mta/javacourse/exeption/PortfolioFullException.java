package com.mta.javacourse.exeption;

public class PortfolioFullException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public PortfolioFullException(){
		super("Maximum portfolio size has been reached!");
	}
}
