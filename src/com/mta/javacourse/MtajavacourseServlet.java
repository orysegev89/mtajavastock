package com.mta.javacourse;

import java.io.IOException;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MtajavacourseServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
		
		/**
		 * Exercise #1
		 * */
		
		int radius = 50;
		double area = 0;
		
		area = 2 * Math.PI * radius;			
		String resultStr1 = new String("<h1>The area of circle with radius " + radius + "cm is: " + area + "square­ cm" + "</h1>" + "</br>");
		resp.getWriter().println(resultStr1);
		
		
		/**
		 * Exercise #2
		 * */
		
		double angleBInRadians = ( 30 * (Math.PI / 180));
		int hypotenuseLength = 50;
		double opposite = Math.sin(angleBInRadians) * hypotenuseLength;
		
		String resultStr2 = new String("<h1>The length of opposite where angle B is 30 degrees and Hypotenuse length is 50 cm is: "
		+ opposite + " cm" + "</h1>" + "</br>");
		resp.getWriter().println(resultStr2);
		
		
		/**
		 * Exercise #3
		 * */
		
		int base = 20, exp = 13;
		int result  = (int) Math.pow(base, exp);
		
		String resultStr3 = new String("<h1>Power of " + base + " with exp of " + exp + " is:" +  result + "</h1>" + "</br>");
				resp.getWriter().println(resultStr3);
	}
}
