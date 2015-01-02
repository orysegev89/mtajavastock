package com.mta.javacourse.model;

//import il.ac.mta.model.Stock;

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
	private StockStatus[] _stocksStatus;
	private int _portfolioSize;
	private float _balance;

	/**
	 * Default Contractor
	 */

	public Portfolio() {
		_balance = 0;
		_portfolioSize = 0;
		_stocksStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
	}

	/**
	 * copy constructor
	 * 
	 * @param stock
	 * @param stockStatus
	 * @param portfolioSize
	 */
	public Portfolio(StockStatus[] stockStatus, int portfolioSize) {
		this();
		for (int i = 0; i < portfolioSize; i++) {
			this._stocksStatus[i] = new StockStatus(stockStatus[i]);
		}
	}

	/**
	 * copy constructor
	 * 
	 * @param instance
	 */
	public Portfolio(Portfolio instance) {
		this(instance._stocksStatus, instance._portfolioSize);
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
			if (_stocksStatus[i].getSymbol().equals(stock.getSymbol())) {
				return false;
			}
		}
		if (_portfolioSize < MAX_PORTFOLIO_SIZE) {
			_stocksStatus[_portfolioSize] = new StockStatus(stock.getSymbol(),
					stock.getAsk(), stock.getBid(), stock.getDate(),
					StockStatus.ALGO_RECOMMENDATION.DO_NOTHING, (int) 0);

			_portfolioSize++;
		} else {
			System.out.println("Can’t add new stock, portfolio can have only "
					+ MAX_PORTFOLIO_SIZE + " stocks!!!");
			return false;
		}
		return true;
	}

	/**
	 * remove stock from portfolio stock list by his symbol
	 * 
	 * @param symbol
	 * @return false for fail such as symbol not found or if try remove when
	 *         portfolio stock list is empty, else true for successfully remove
	 *         stock
	 */

	public boolean removeStock(String symbol) {
		boolean _existFlag = false;
		StockStatus[] _tempStockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
		int j = 0;

		for (int i = 0; i < _portfolioSize; i++) {

			if (_stocksStatus[i].getSymbol().equals(symbol)) {
				sellStock(_stocksStatus[i].getSymbol(), -1);
				_portfolioSize--;
				_existFlag = true;
			} else {
				_tempStockStatus[j] = new StockStatus(_stocksStatus[i]);
				j++;
			}
		}
		_stocksStatus = _tempStockStatus;

		if (_existFlag == false) {
			System.out.println(symbol + "does not exist in the portfolio");
		}
		return _existFlag;
	}

	/**
	 * sell stock
	 * 
	 * @param symbol
	 *            - stock symbol that want to sell
	 * @param quantity
	 *            - sell amount (-1 will sell whole stock)
	 * @return false for fail such as is stock not exist or portfolio stock list
	 *         is empty
	 */

	public boolean sellStock(String symbol, int quantity) {

		float _tempBalanceSum;

		for (int i = 0; i < _portfolioSize; i++) {
			if (_stocksStatus[i].getSymbol().equals(symbol)) {
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
					_tempBalanceSum = quantity * _stocksStatus[i]._bid;
					updateBalance(_tempBalanceSum);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * buy stock
	 * 
	 * @param symbol
	 *            - stock symbol that want to buy
	 * @param quantity
	 *            - buy amount (-1 will buy whole stock according to the
	 *            portfolio balance)
	 * @return false for fail such as is stock not exist or portfolio stock list
	 *         is empty, true for successfully buying
	 */

	public boolean buyStock(String symbol, int quantity) {

		float _tempBalanceSum;
		boolean flag = false;

		for (int i = 0; i < _portfolioSize; i++) {
			if (_stocksStatus[i].getSymbol().equals(symbol)) {
				if (quantity == -1) {
					quantity = (int) Math.floor(_balance
							/ _stocksStatus[i]._ask);
				}
				_tempBalanceSum = quantity * _stocksStatus[i]._ask;
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
			sum += _stocksStatus[i]._bid * _stocksStatus[i]._stockQuantity;
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
		
		StringBuilder str = new StringBuilder();

		str.append("<div style=\"margin:0 auto;width: 50%;"
				+ "background-color: #BCF5A9;text-align:center;font-weight:bold;font-size:220%\">");
		str.append(getTitle());
		str.append("<br>");
		str.append("</div><p>");
		str.append("<br>");

		
		str.append("<b>Total Portfolio value: </b>");
		str.append(getTotalValue());
		str.append("$");
		str.append("<br>");

		str.append("<b>Total Stocks value: </b>");
		str.append(getStocksValue());
		str.append("$");
		str.append("<br>");
		
		str.append("<b>Balance: </b>");
		str.append(getBalance());
		str.append("$");
		str.append("<br>");

		for (int i = 0; i < _portfolioSize; i++) {
			Stock stock = _stocksStatus[i];
			if (stock == null)
				continue;

			str.append(stock.getHtmlDescription());
			str.append("<br>");
		}
		return str.toString();
	}

	public void updateBalance(float amount) {
		if (this._balance + amount < 0) {
			System.out.println("ERROR - balance can not be negative!");
		} else {
			this._balance += amount;
		}
	}
}
