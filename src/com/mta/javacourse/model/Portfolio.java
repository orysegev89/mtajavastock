package com.mta.javacourse.model;

import com.mta.javacourse.exeption.BalanceException;
import com.mta.javacourse.exeption.NotEnoughQuantityException;
import com.mta.javacourse.exeption.PortfolioFullException;
import com.mta.javacourse.exeption.StockAlreadyExistsException;
import com.mta.javacourse.exeption.StockNotExistException;



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
	 * @throws StockAlreadyExistsException 
	 * @throws PortfolioFullException 
	 */

	public void addStock(Stock stock) throws StockAlreadyExistsException, PortfolioFullException {
		for (int i = 0; i < _portfolioSize; i++) {
			if (_stocksStatus[i].getSymbol().equals(stock.getSymbol())) {
				throw new StockAlreadyExistsException(stock.getSymbol());
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
			throw new PortfolioFullException();
		}
	}

	/**
	 * remove stock from portfolio stock list by his symbol
	 * 
	 * @param symbol
	 *
	 * @throws StockNotExistException 
	 * @throws NotEnoughQuantityException 
	 * @throws BalanceException 
	 */

	public void removeStock(String symbol) throws StockNotExistException, NotEnoughQuantityException, BalanceException {
		boolean _existFlag = false;
		StockStatus[] _tempStockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
		int j = 0;

		for (int i = 0; i < _portfolioSize; i++) {

			if (_stocksStatus[i].getSymbol().equals(symbol)) {
				sellStock(_stocksStatus[i].getSymbol(), -1);
				_portfolioSize--;
				_existFlag=true;
			} else {
				_tempStockStatus[j] = new StockStatus(_stocksStatus[i]);
				j++;
			}
		}
		_stocksStatus = _tempStockStatus;

		if (_existFlag == false) {
			System.out.println(symbol + "does not exist in the portfolio");
			throw new StockNotExistException(symbol);
		}
	}

	/**
	 * sell stock
	 * 
	 * @param symbol
	 *            - stock symbol that want to sell
	 * @param quantity
	 *            - sell amount (-1 will sell whole stock)
	 * @throws NotEnoughQuantityException 
	 * @throws BalanceException 
	 * @throws StockNotExistException 
	 */

	public void sellStock(String symbol, int quantity) throws NotEnoughQuantityException, BalanceException, StockNotExistException {

		float _tempBalanceSum;

		for (int i = 0; i < _portfolioSize; i++) {
			if (_stocksStatus[i].getSymbol().equals(symbol)) {
				if (quantity > _stocksStatus[i]._stockQuantity) {
					System.out.println("There are only"
							+ _stocksStatus[i]._stockQuantity + " in "
							+ _stocksStatus[i]._symbol + " - cant sell "
							+ quantity);
					throw new NotEnoughQuantityException(symbol,quantity);
				} else {
					if (quantity == -1) {
						quantity = _stocksStatus[i]._stockQuantity;
					}
					_stocksStatus[i]._stockQuantity -= quantity;
					_tempBalanceSum = quantity * _stocksStatus[i]._bid;
					updateBalance(_tempBalanceSum);
					return;
				}
			}
		}
		throw new StockNotExistException(symbol);
	}

	/**
	 * buy stock
	 * 
	 * @param symbol
	 *            - stock symbol that want to buy
	 * @param quantity
	 *            - buy amount (-1 will buy whole stock according to the
	 *            portfolio balance)
	 * @throws StockNotExistException 
	 */

	public void buyStock(String symbol, int quantity) throws BalanceException, StockNotExistException{

		float _tempBalanceSum;

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
					throw new BalanceException();
				} else {
					updateBalance(-1 * _tempBalanceSum);
					_stocksStatus[i]._stockQuantity += quantity;
				}
				return;
			}
		}
		throw new StockNotExistException(symbol);
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

	public void updateBalance(float amount) throws BalanceException {
		if (this._balance + amount < 0) {
			System.out.println("ERROR - balance can not be negative!");
			throw new BalanceException();
		} else {
			this._balance += amount;
		}
	}
}
