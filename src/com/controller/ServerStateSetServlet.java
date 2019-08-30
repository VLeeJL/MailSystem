package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.ServerConfig;
import com.services.ServerStart;

/**
 * Servlet implementation class ServerStateSet
 */
@WebServlet("/ServerStateSetServlet")
public class ServerStateSetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ServerStateSetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("SmtpState") != null) {
			System.out.println("收到SMTP 参数");
			ServerConfig.smtpServer = ServerConfig.smtpServer ? false : true;
			if (ServerConfig.smtpServer) {
				ServerStart.SMTPStart();
			} else {
				ServerStart.SMTPClose();
			}
		}
		if (request.getParameter("Pop3state") != null) {
			System.out.println("收到POP3 参数");
			ServerConfig.pop3Server = ServerConfig.pop3Server ? false : true;
			if (ServerConfig.pop3Server) {
				ServerStart.POP3Start();
			} else {
				ServerStart.POP3Close();
			}
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("daohang1.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
