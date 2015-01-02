package com.mta.javacourse.service;

import java.util.Calendar;
import java.util.Date;

import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.model.Stock;

/**
 * class that handles service requests of front­end (html side) to data model
 * 
 * @author orysegev
 *
 */
public class PortfolioService {

	public PortfolioService() {

	}

	/**
	 * in order to set an individual portfolio title
	 * 
	 * @param title
	 * @return
	 */
	public Portfolio getPortfolio() {

		Portfolio myPortfolio = new Portfolio();
		myPortfolio.setTitle("Exercise 7 portfolio");
		myPortfolio.updateBalance(10000);

		Stock stock1 = new Stock();
		Stock stock2 = new Stock();
		Stock stock3 = new Stock();

		stock1.setSymbol("PIH");
		stock1.setAsk((float) 10.0);
		stock1.setBid((float) 8.5);

		Calendar c1 = Calendar.getInstance();
		c1.set(2014, 12, 15, 0, 0, 0);
		Date date1 = c1.getTime();
		stock1.setDate(date1);

		stock2.setSymbol("AAL");
		stock2.setAsk((float) 30.0);
		stock2.setBid((float) 25.5);

		Calendar c2 = Calendar.getInstance();
		c2.set(2014, 12, 15, 0, 0, 0);
		Date date2 = c1.getTime();
		stock2.setDate(date2);

		stock3.setSymbol("CAAS");
		stock3.setAsk((float) 20.0);
		stock3.setBid((float) 15.5);

		Calendar c3 = Calendar.getInstance();
		c3.set(2014, 12, 15, 0, 0, 0);
		Date date3 = c3.getTime();
		stock3.setDate(date3);

		myPortfolio.addStock(stock1);
		myPortfolio.addStock(stock2);
		myPortfolio.addStock(stock3);
		
		myPortfolio.buyStock("PIH", 20);
		myPortfolio.buyStock("AAL", 30);
		myPortfolio.buyStock("CAAS", 40);
		
		myPortfolio.sellStock("AAL", -1);
		myPortfolio.removeStock("CAAS");
		

		return myPortfolio;
	}

}
