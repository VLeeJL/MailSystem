package com.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.services.AccountService;
import com.services.ServerConfigService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public LoginServlet() throws SQLException {
    	ServerConfigService.getServerConfig();
    	AccountService.getAccountList();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String account_name = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if (AccountService.loginAccount(account_name, password)) {
				request.getSession().setAttribute("account_name", account_name);	
				response.sendRedirect(request.getContextPath()+"/daohang1.jsp");
			}
			else {
				response.sendRedirect(request.getContextPath()+"/loginFail.jsp");
			}
		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
