package com.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.services.AccountService;
import com.services.MailService;

/**
 * Servlet implementation class SendMessage
 */
@WebServlet("/SendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		boolean flag = true;
		String title=request.getParameter("title");
		String receiverStr = request.getParameter("receiver");
		String content=request.getParameter("mailContent");
		System.out.println("title" + title);
		System.out.println("receiverStr" + receiverStr);
		System.out.println("content" + content);
		ArrayList<String> receiverArrList = null;
		if ((title == null) || receiverStr == null || content == null) {			
			flag = false;			
		}
		else {
			receiverArrList = new ArrayList<String>(Arrays.asList(receiverStr.split(" ")));
			for (String accountName : receiverArrList) {
				try {
					if (!AccountService.queryAccountName(accountName)) {	
						flag = false;
						break;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}		
		
		System.out.println("flag" + flag);
		String account_name = (String) request.getSession().getAttribute("account_name");		
		try {
			if (flag) {
				if (!MailService.addMail(account_name, receiverArrList, title, content))
					flag = false;//∑¢ÀÕ ß∞‹
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		if (flag) {
			response.sendRedirect(request.getContextPath()+"/daohang3.jsp");
		}
		else {
			System.out.println("≤‚ ‘£∫     ");
			PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 1024, true);
			pageContext.setAttribute("Error", "error");
			request.getRequestDispatcher("/daohang3.jsp").forward(request, response);
		}
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
