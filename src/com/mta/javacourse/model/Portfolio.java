package com.mta.javacourse.model;

import java.util.Date;

/**
 * to handle the data model - stocks
 * 
 * @category portfolio class
 * @author orysegev
 *
 */
public class Portfolio {

	public enum ALGO_RECOMMENDATION {
		DO_NOTHING, BUY, SELL
	}

	private static final int MAX_PORTFOLIO_SIZE = 5;
	private String _title = "portfolio1";
	private Stock[] _stocks;
	private StockStatus[] _stocksStatus;
	private int _portfolioSize;
	private float _balance;

	/**
	 * Default Contractor
	 */

	public Portfolio() {
		_balance = 0;
		_portfolioSize = 0;
		_stocks = new Stock[MAX_PORTFOLIO_SIZE];
		_stocksStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
	}

	/**
	 * copy constructor
	 * 
	 * @param stock
	 * @param stockStatus
	 * @param portfolioSize
	 */
	public Portfolio(Stock[] stock, StockStatus[] stockStatus, int portfolioSize) {
		this();
		for (int i = 0; i < portfolioSize; i++) {
			this._stocks[i] = new Stock(stock[i]);
			this._stocksStatus[i] = new StockStatus(stockStatus[i]);
		}
	}

	/**
	 * copy constructor
	 * 
	 * @param instance
	 */
	public Portfolio(Portfolio instance) {
		this(instance._stocks, instance._stocksStatus, instance._portfolioSize);
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String _title) {
		this._title = _title;
	}

	/**
	 * add stock to Portfolio with Stock Status
	 * 
	 * @param stock
	 * @return false if stock not exist, else return true for success
	 */

	public boolean addStock(Stock stock) {
		for (int i = 0; i < _portfolioSize; i++) {
			if (_stocks[i].getSymbol() == stock.getSymbol()) {
				return false;
			}
		}
		if (_portfolioSize < MAX_PORTFOLIO_SIZE) {
			_stocks[_portfolioSize] = new Stock(stock);

			_stocksStatus[_portfolioSize] = new StockStatus(
					_stocks[_portfolioSize].getSymbol(),
					_stocks[_portfolioSize].getBid(),
					_stocks[_portfolioSize].getAsk(),
					_stocks[_portfolioSize].getDate(),
					ALGO_RECOMMENDATION.DO_NOTHING, (int) 0);
			_portfolioSize++;
		} else {
			System.out.println("Can’t add new stock, portfolio can have only "
					+ MAX_PORTFOLIO_SIZE + " stocks!!!");
			return false;
		}
		return true;
	}

	public Stock[] getStock() {
		return _stocks;
	}

	/**
	 * remove stock from portfolio stock list by his symbol
	 * 
	 * @param symbol
	 * @return false for fail such as symbol not found or if try remove when
	 *         portfolio stock list is empty, else true for successfully remove stock
	 */

	public boolean removeStock(String symbol) {
		boolean _existFlag = false;
		Stock[] _tempStocks = new Stock[MAX_PORTFOLIO_SIZE];
		StockStatus[] _tempStockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
		int j = 0;

		for (int i = 0; i < _portfolioSize; i++) {

			if (symbol == _stocks[i].getSymbol()) {
				sellStock(_stocks[i].getSymbol(), -1);
				_portfolioSize--;
				_existFlag = true;
			} else {
				_tempStocks[j] = new Stock(_stocks[i]);
				_tempStockStatus[j] = new StockStatus(_stocksStatus[i]);
				j++;
			}
		}
		_stocks = _tempStocks;
		_stocksStatus = _tempStockStatus;

		if (_existFlag == false) {
			System.out.println(symbol + "does not exist in the portfolio");
		}
		return _existFlag;
	}

	/**
	 * sell stock
	 * @param symbol - stock symbol that want to sell
	 * @param quantity - sell amount (-1 will sell whole stock)
	 * @return false for fail such as is stock not exist or portfolio stock list is empty
	 */
	
	public boolean sellStock(String symbol, int quantity) {

		float _tempBalanceSum;

		for (int i = 0; i < _portfolioSize; i++) {
			if (symbol == _stocks[i].getSymbol()) {
				if (quantity > _stocksStatus[i]._stockQuantity) {
					System.out.println("There are only"
							+ _stocksStatus[i]._stockQuantity + " in "
							+ _stocksStatus[i]._symbol + " - cant sell "
							+ quantity);
					return false;
				} else {
					if (quantity == -1) {
						quantity = _stocksStatus[i]._stockQuantity;
					}
					_stocksStatus[i]._stockQuantity -= quantity;
					_tempBalanceSum = quantity * _stocksStatus[i]._currentBid;
					updateBalance(_tempBalanceSum);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * buy stock
	 * @param symbol - stock symbol that want to buy
	 * @param quantity - buy amount (-1 will buy whole stock according to the portfolio balance)
	 * @return false for fail such as is stock not exist or portfolio stock list is empty,
	 * 			true for successfully buying
	 */
	
	public boolean buyStock(String symbol, int quantity) {

		float _tempBalanceSum;
		boolean flag = false;

		for (int i = 0; i < _portfolioSize; i++) {
			if (symbol == _stocks[i].getSymbol()) {
				if (quantity == -1) {
					quantity = (int) Math.floor(_balance
							/ _stocksStatus[i]._currentAsk);
				}
				_tempBalanceSum = quantity * _stocksStatus[i]._currentAsk;
				if (_tempBalanceSum > _balance) {
					System.out.println("Not enough money to buy " + quantity
							+ " stocks of kind " + _stocksStatus[i]._symbol);
				} else {
					updateBalance(-1 * _tempBalanceSum);
					_stocksStatus[i]._stockQuantity += quantity;
					flag = true;
				}
			}
		}
		return flag;
	}

	public float getStocksValue() {

		float sum = 0;

		for (int i = 0; i < _portfolioSize; i++) {
			sum += _stocksStatus[i]._currentBid
					* _stocksStatus[i]._stockQuantity;
		}
		return sum;
	}

	public float getBalance() {
		return _balance;
	}

	public float getTotalValue() {
		return (getBalance() + getStocksValue());
	}

	public String getHtmlString() {
		String str;
		str = "<h1>" + _title + "</h1>";
		for (int i = 0; i < _portfolioSize; i++) {
			str += _stocks[i].getHtmlDescription();
		}
		str += " Total Portfolio Value: " + getTotalValue() + " $ "
				+ "Total Stocks value: " + getStocksValue() + " $ "
				+ "Balance: " + getBalance() + " $ ";
		return str;
	}

	public void updateBalance(float amount) {
		if (this._balance + amount < 0) {
			System.out.println("ERROR - balance can not be negative!");
		} else {
			this._balance += amount;
		}
	}

	private class StockStatus {
		String _symbol;
		float _currentBid;
		float _currentAsk;
		Date _date;
		int _stockQuantity;
		ALGO_RECOMMENDATION _recommendation;

		public StockStatus() {
		}

		public StockStatus(String symbol, float currentBid, float currentAsk,
				Date date, ALGO_RECOMMENDATION recommendation, int stockQuantity) {
			this();
			this._symbol = symbol;
			this._currentBid = currentBid;
			this._currentAsk = currentAsk;
			this._date = new Date(date.getTime());
			this._recommendation = recommendation;
			this._stockQuantity = stockQuantity;
		}

		public StockStatus(StockStatus instance) {
			this(instance._symbol, instance._currentBid, instance._currentAsk,
					instance._date, instance._recommendation,
					instance._stockQuantity);
		}

		// stockStatus end inner class
	}

}
