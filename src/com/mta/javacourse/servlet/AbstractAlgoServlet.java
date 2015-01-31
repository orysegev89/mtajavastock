package com.mta.javacourse.servlet;


import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mta.javacourse.service.PortfolioService;

/**
 * @author hanan.gitliz@gmail.com
 * @since Jan 12, 2015
 */
public abstract class AbstractAlgoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected final Logger log = Logger.getLogger(AbstractAlgoServlet.class.getSimpleName());
	protected PortfolioService portfolioService;
	private GsonBuilder gsonBuilder;
	
	@Override
	public void init() throws ServletException {
		super.init();
		portfolioService = PortfolioService.getInstance();
		gsonBuilder = new GsonBuilder();
	}
	
	protected Gson withNullObjects() {
		return gsonBuilder.serializeNulls().create();
	}
	
	protected Gson withoutNullObjects() {
		return gsonBuilder.create();
	}
}
