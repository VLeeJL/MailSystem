package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.services.AccountService;
import com.services.FilterService;

/**
 * Servlet implementation class IPIntercept
 */
@WebServlet("/InterceptServlet")
public class InterceptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public InterceptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String intercept_ip=request.getParameter("ip");
		String intercept_account = request.getParameter("account_name");
		if (intercept_ip != null) {
			try {
				FilterService.addFilterAddress(intercept_ip);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			if ((intercept_account != null) && AccountService.queryAccountName(intercept_account)) {
				try {				
					FilterService.addFilterAccounts(intercept_account);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("daohang4.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
