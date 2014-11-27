package com.mta.javacourse;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mta.javacourse.Stock;

public class StockDetailsServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		resp.setContentType("text/html");
		Stock stock1 = new Stock();
		Stock stock2 = new Stock();
		Stock stock3 = new Stock();
		
		
		stock1.setSymbol("PIH");
		stock1.setAsk((float) 12.4);
		stock1.setBid((float) 13.1);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(2014, 11, 15, 0, 0, 0);
		Date date1 = c1.getTime();
		stock1.setDate(date1);
		
		stock2.setSymbol("AAL");
		stock2.setAsk((float) 5.5);
		stock2.setBid((float) 5.78);
		
		Calendar c2 = Calendar.getInstance();
		c2.set(2014, 11, 15, 0, 0, 0);
		Date date2 = c1.getTime();
		stock2.setDate(date2);
		
		stock3.setSymbol("CAAS");
		stock3.setAsk((float)31.5);
		stock3.setBid((float)31.2);
		
		Calendar c3 = Calendar.getInstance();
		c3.set(2014, 11, 15, 0, 0, 0);
		Date date3 = c3.getTime();
		stock3.setDate(date3);
		
		resp.getWriter().println("<h2>The stocks are:</h2><p>");
		resp.getWriter().println("<br>" + stock1.getHtmlDescription() + "<br>");
		resp.getWriter().println("<br>" + stock2.getHtmlDescription() + "<br>");
		resp.getWriter().println("<br>" + stock3.getHtmlDescription() + "<br>");

	}

}
