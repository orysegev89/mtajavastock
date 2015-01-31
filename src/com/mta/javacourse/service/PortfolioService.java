package com.mta.javacourse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.mta.javacourse.dto.PortfolioTotalStatus;
import com.mta.javacourse.exeption.BalanceException;
import com.mta.javacourse.exeption.IllegalQuantityException;
import com.mta.javacourse.exeption.NotEnoughQuantityException;
import com.mta.javacourse.exeption.PortfolioFullException;
import com.mta.javacourse.exeption.StockAlreadyExistsException;
import com.mta.javacourse.exeption.StockNotExistException;
import com.mta.javacourse.exeption.StockNotExistsException;
import com.mta.javacourse.exeption.SymbolNotFoundInNasdaq;
import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.model.Stock;
import com.mta.javacourse.model.StockStatus;

/**
 * @author hanan.gitliz@gmail.com
 */
public class PortfolioService {

	private final static Logger log = Logger.getLogger(PortfolioService.class
			.getSimpleName());

	private Portfolio portfolio;

	public enum OPERATION {
		ADD, REMOVE, SELL, BUY
	}

	private static final int DAYS_BACK = 30;
	private static PortfolioService instance = new PortfolioService();

	public static PortfolioService getInstance() {
		return instance;
	}

	private DatastoreService datastoreService;

	private PortfolioService() {
		datastoreService = DatastoreService.getInstance();
	}

	public Portfolio getPortfolio() {
		if (portfolio == null) {
			portfolio = datastoreService.loadPortfolilo();
		}

		return portfolio;
	}

	/**
	 * Updates Portfolio with algo recommendation.
	 */
	public void update() {
		StockStatus[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.SIZE);
		for (StockStatus stockStatus : stocks) {
			symbols.add(stockStatus.getSymbol());
		}

		List<StockStatus> update = new ArrayList<>(Portfolio.SIZE);
		List<Stock> currentStocksList;
		try {
			currentStocksList = MarketService.getInstance().getStocks(symbols);
			for (Stock stock : currentStocksList) {
				update.add(new StockStatus((StockStatus) stock));
			}

			datastoreService.saveToDataStore(update);

			// load fresh data from database.
			portfolio = null;
		} catch (SymbolNotFoundInNasdaq e) {
			log.severe(e.getMessage());
		}
	}

	public PortfolioTotalStatus[] getPortfolioTotalStatus() {

		Portfolio portfolio = getPortfolio();
		Map<Date, Float> map = new HashMap<>();

		// get stock status from db.
		Stock[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length; i++) {
			Stock stock = stocks[i];

			if (stock != null) {
				List<StockStatus> history = datastoreService.getStockHistory(
						stock.getSymbol(), DAYS_BACK);

				for (int j = 0; j < history.size(); j++) {
					StockStatus curr = history.get(j);
					Date date = dateMidnight(curr.getDate());
					float value = curr.getBid() * curr.getStockQuantity();

					Float total = map.get(date);
					if (total == null) {
						total = value;
					} else {
						total += value;
					}

					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		// create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}

		// sort by date ascending.
		Arrays.sort(ret);

		return ret;
	}

	public void setTitle(String title) {
		Portfolio portfolio = getPortfolio();
		portfolio.setTitle(title);
		datastoreService.updatePortfolio(portfolio);

		flush();
	}

	public void setBalance(float newBalance) throws BalanceException {
		Portfolio portfolio = getPortfolio();
		portfolio.updateBalance(newBalance);
		datastoreService.updatePortfolio(portfolio);

		flush();
	}

	public void addStock(String symbol) throws StockAlreadyExistsException,
			PortfolioFullException, StockNotExistsException,
			SymbolNotFoundInNasdaq, StockNotExistException {
		Portfolio portfolio = getPortfolio();

		// get current symbol values from nasdaq.
		Stock stock = MarketService.getInstance().getStock(symbol);

		if (stock != null) {

			// first thing, add it to portfolio.
			portfolio.addStock(stock);

			// second thing, save the new stock to the database.
			datastoreService.saveStock(portfolio.findBySymbol(symbol));

			flush();
		}
	}

	public void buyStock(String symbol, int quantity) throws BalanceException,
			StockNotExistsException, StockNotExistException {
		getPortfolio().buyStock(symbol, quantity);
		flush();
	}

	public void sellStock(String symbol, int quantity)
			throws StockNotExistsException, IllegalQuantityException,
			NotEnoughQuantityException, BalanceException,
			StockNotExistException {
		getPortfolio().sellStock(symbol, quantity);
		flush();
	}

	public void removeStock(String symbol) throws StockNotExistsException,
			IllegalQuantityException, StockNotExistException,
			NotEnoughQuantityException, BalanceException {
		getPortfolio().removeStock(symbol);
		flush();
	}

	private void flush() {
		// update db
		datastoreService.updatePortfolio(getPortfolio());
		// now make next call to portfolio to fetch data from updated db.
		portfolio = null;
	}

	/**
	 * Transform a given date to start day date.
	 * 
	 * @param date
	 * @return
	 */
	private Date dateMidnight(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
}
