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

import com.dao.AccountDao;
import com.services.AccountService;

@WebServlet("/AndroidRegisterServlet")
public class AndroidRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AndroidRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String account_name = request.getParameter("account");
		String password = request.getParameter("password");
		System.out.println("AndroidRegisterServlet" + "account_name:   " + account_name);
		System.out.println("password:   " + password);		
		String isRegisterSuccess = "true";
		try {
			if (AccountService.queryAccountName(account_name)) //若用户名存在则进行密码验证
				isRegisterSuccess = "false";
			else {
				AccountDao.saveAccount(AccountService.registerAccount(account_name, password, 2));//注册用户
			}				
		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.write(isRegisterSuccess);  
		out.flush();  
        out.close(); 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
