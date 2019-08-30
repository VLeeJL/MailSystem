package com.androidController;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.services.AccountService;

/**
 * Servlet implementation class AndroidChangePassowordServlet
 */
@WebServlet("/AndroidChangePassowordServlet")
public class AndroidChangePassowordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public AndroidChangePassowordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");		
		String account_name = request.getParameter("account");
		String password = request.getParameter("password");
		String isChangeSuccess = "false";
		System.out.println("account_name:   " + account_name);
		System.out.println("password:   " + password);
		try {
			if (AccountService.updateAccountPassword(account_name, password) ) {
				isChangeSuccess = "true";
			}
		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.write(isChangeSuccess);  
		out.flush();  
        out.close(); 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
