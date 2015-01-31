package com.mta.javacourse.dto;

import java.util.List;

import com.mta.javacourse.model.StockStatus;

/**
 * @author hanan.gitliz@gmail.com
 * @since Jan 12, 2015
 */
public class PortfolioDto {

	private String title;
	private PortfolioTotalStatus[] totalStatus;
	private List<StockStatus> stockStatusList;

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public PortfolioTotalStatus[] getTotalStatus() {
		return totalStatus;
	}
	
	public void setTotalStatus(PortfolioTotalStatus[] totalStatus) {
		this.totalStatus = totalStatus;
	}

	public List<StockStatus> getStockStatusList() {
		return stockStatusList;
	}
	
	public void setStockTable(List<StockStatus> stockStatusList) {
		this.stockStatusList = stockStatusList;
	}
}
