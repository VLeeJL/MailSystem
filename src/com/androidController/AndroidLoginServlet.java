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
import com.services.ParseJsonObject;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AndroidLoginServlet
 */
@WebServlet("/AndroidLoginServlet")
public class AndroidLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AndroidLoginServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String account_name = request.getParameter("account");
		String password = request.getParameter("password");
		String isLoginSuccess = "false";
		try {
			if (AccountService.queryAccountName(account_name)) //若用户名存在则进行密码验证
				if (AccountService.loginAccount(account_name, password)) {
					isLoginSuccess = "true";
				}
		} catch (SQLException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		out.write(isLoginSuccess);  
		out.flush();  
        out.close(); 
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
