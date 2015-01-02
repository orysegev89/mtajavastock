package com.mta.javacourse.model;

import java.util.Date;

public class StockStatus extends Stock {

	public static enum ALGO_RECOMMENDATION {
		DO_NOTHING, BUY, SELL
	}

	ALGO_RECOMMENDATION _recommendation;
	int _stockQuantity;

	public StockStatus() {
		_stockQuantity = 0;
		_recommendation = ALGO_RECOMMENDATION.DO_NOTHING;
	}

	public StockStatus(String symbol, float ask, float bid, Date date,
			ALGO_RECOMMENDATION recommendation, int stockQuantity) {
		super(symbol, ask, bid, new Date(date.getTime()));
		this._recommendation = recommendation;
		this._stockQuantity = stockQuantity;
	}

	public StockStatus(StockStatus instance) {
		this(instance.getSymbol(), instance.getAsk(), instance.getBid(),
				instance.getDate(), instance._recommendation,
				instance._stockQuantity);
	}

	public int getStockQuantity() {
		return _stockQuantity;
	}

	public void setStockQuantity(int _stockQuantity) {
		this._stockQuantity = _stockQuantity;
	}

	public ALGO_RECOMMENDATION getRecommendation() {
		return _recommendation;
	}

	public void setRecommendation(ALGO_RECOMMENDATION _recommendation) {
		this._recommendation = _recommendation;
	}

}
