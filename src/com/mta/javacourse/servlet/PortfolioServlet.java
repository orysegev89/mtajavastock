package com.mta.javacourse.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mta.javacourse.exeption.BalanceException;
import com.mta.javacourse.exeption.NotEnoughQuantityException;
import com.mta.javacourse.exeption.PortfolioFullException;
import com.mta.javacourse.exeption.StockNotExistException;
import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.service.PortfolioService;
/**
 * class that sends data to front end
 * @author orysegev
 *
 */
public class PortfolioServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1913136625667734922L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		
		PortfolioService portfolioService = new PortfolioService();
		Portfolio portfolio1;
		try {
			portfolio1 = portfolioService.getPortfolio();
			resp.getWriter().println(portfolio1.getHtmlString());
		} catch (StockNotExistException | PortfolioFullException
				| BalanceException | NotEnoughQuantityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//resp.getWriter().println(portfolio1.getHtmlString());
	}
	

}
