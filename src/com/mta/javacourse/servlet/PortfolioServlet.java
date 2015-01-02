package com.mta.javacourse.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.runtime.ProtectedFunctionMapper;

import com.mta.javacourse.model.Portfolio;
import com.mta.javacourse.service.PortfolioService;
/**
 * class that sends data to front end
 * @author orysegev
 *
 */
public class PortfolioServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html");
		
		PortfolioService portfolioService = new PortfolioService();
		Portfolio portfolio1 = portfolioService.getPortfolio("Exercise 7 portfolio");
		resp.getWriter().println(portfolio1.getHtmlString());
	}
	

}
