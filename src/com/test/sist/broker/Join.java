package com.test.sist.broker;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/broker/join.do")
public class Join extends HttpServlet {

	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   
	      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/broker/join.jsp");
	      dispatcher.forward(request, response);
	      
	   }

}
