package com.mta.javacourse;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MtajavacourseServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		int num1,num2,num3,result;
	
		num1 = 4;
		num2 = 3;
		num3 = 7;
		result = (num1 + num2) * num3;
		
		String resultStr = new String("<h1>The result of ( "+"" +num1+" + "+num2+" ) * "+num3+" = "+result+"</h1>");
		
		resp.getWriter().println(resultStr);
	}
}
