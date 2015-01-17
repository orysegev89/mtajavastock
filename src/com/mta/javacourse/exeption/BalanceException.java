package com.mta.javacourse.exeption;

public class BalanceException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public BalanceException() {
		super("Portfolio balance can not be negative!");
	}
}
