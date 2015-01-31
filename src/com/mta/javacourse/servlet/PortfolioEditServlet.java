package com.mta.javacourse.servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mta.javacourse.dto.PortfolioEditDto;
import com.mta.javacourse.exeption.BalanceException;
import com.mta.javacourse.exeption.IllegalQuantityException;
import com.mta.javacourse.exeption.PortfolioFullException;
import com.mta.javacourse.exeption.StockAlreadyExistsException;
import com.mta.javacourse.exeption.StockNotExistsException;
import com.mta.javacourse.exeption.SymbolNotFoundInNasdaq;
import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.model.StockStatus;
import com.mta.javacourse.service.PortfolioService;
import com.mta.javacourse.service.PortfolioService.OPERATION;

public class PortfolioEditServlet extends AbstractAlgoServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");

		Portfolio portfolio = portfolioService.getPortfolio();
		StockStatus[] stockStatusArray = portfolio.getStocks();
		List<StockStatus> stockStatusList = new ArrayList<>();
		for (StockStatus ss : stockStatusArray) {
			if(ss != null)
				stockStatusList.add(ss);
		}

		PortfolioEditDto ret = new PortfolioEditDto();
		ret.setTitle(portfolio.getTitle());
		ret.setBalance(portfolio.getBalance());
		ret.setStockStatusList(stockStatusList);

		resp.getWriter().print(withNullObjects().toJson(ret));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");

		try {
			String title = req.getParameter("title");
			if(title != null && !title.isEmpty()) {
				portfolioService.setTitle(title);
			}

			String balance = req.getParameter("balance");
			if(balance != null && !balance.isEmpty()) {
				portfolioService.setBalance(Float.valueOf(balance));
			}

			String symbol = req.getParameter("symbol");

			String qStr = req.getParameter("quantityforaction");
			int quantity;
			if(qStr == null || qStr.isEmpty()) {
				quantity = -1;
			}else {
				quantity = Integer.valueOf(qStr);					
			}

			String operationStr = req.getParameter("operation");
			if (operationStr != null) {
				PortfolioService.OPERATION operation = OPERATION.valueOf(operationStr.toUpperCase());

				switch (operation) {
				case ADD:
					portfolioService.addStock(symbol);
					break;

				case BUY:
					portfolioService.buyStock(symbol, quantity);
					break;

				case SELL:
					portfolioService.sellStock(symbol, quantity);
					break;

				case REMOVE:
					portfolioService.removeStock(symbol);
					break;

				default:
					break;
				}
			}
			resp.sendRedirect("/portfolioedit.jsp");
		}catch(StockNotExistsException e) {
			resp.sendRedirect("/portfolioedit.jsp?error=Stock not exist in portfolio");
		}catch(StockAlreadyExistsException e) {
			resp.sendRedirect("/portfolioedit.jsp?error=Stock already exists in portfolio");
		}catch(PortfolioFullException | SymbolNotFoundInNasdaq | IllegalQuantityException | BalanceException e) {
			resp.sendRedirect("/portfolioedit.jsp?error=" + e.getMessage());
		}catch(Exception e) {
			log.severe("Error: " + e.getMessage());
			resp.sendRedirect("/portfolioedit.jsp?error=General error occured.");
		}
	}
}
