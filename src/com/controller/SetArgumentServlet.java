package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.ServerConfig;
import com.services.ServerConfigService;

/**
 * Servlet implementation class SetArgument
 */
@WebServlet("/SetArgumentServlet")
public class SetArgumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetArgumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pop3_port=request.getParameter("pop_port");
		String smtp_port=request.getParameter("smtp_port");
		String address=request.getParameter("address");
		String syslog_size=request.getParameter("syslog_size");
		try {
			if (pop3_port != null) {
				System.out.println("pop3_port: " + pop3_port);
				ServerConfigService.updateServerConfigPop3Port(Integer.parseInt(pop3_port));
			}
			if (smtp_port != null) {
				System.out.println("smtp_port: " + smtp_port);
				ServerConfigService.updateServerConfigSmtpPort(Integer.parseInt(smtp_port));
			}
			if (address != null) {
				System.out.println("address: " + address);
				ServerConfigService.updateServerConfigDomain(address);
			}
			if (syslog_size != null) {
				System.out.println("syslog_size: " + syslog_size);
				ServerConfigService.updateServerConfigJournalSize(Integer.parseInt(syslog_size));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("pop3_port", ServerConfig.pop3_port);
		request.setAttribute("smtp_port", ServerConfig.smtp_port);
		request.setAttribute("address", ServerConfig.domain);
		request.setAttribute("syslog_size", ServerConfig.journal_size);
		request.getRequestDispatcher("/daohang2.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
