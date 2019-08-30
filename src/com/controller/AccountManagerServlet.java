package com.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Account;
import com.entity.AccountList;

/**
 * Servlet implementation class AccountManagerServlet
 */
@WebServlet("/AccountManagerServlet")
public class AccountManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int PAGE_SIZE = 5;
     
    public AccountManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");//页面保证传过来的页面数不会大于PAGE_SIZE
		HttpSession session=request.getSession();
		int accountLines = AccountList.accountList.size();// 查询总记录数
		int currPage = 0;
		if (request.getParameter("page") != null) {//判断是否是第一次访问页面，若是第一次则为null			
			currPage = Integer.parseInt(request.getParameter("page"));// 对当前页码赋值			
		}	
		ArrayList<Account> pageList = new ArrayList<Account>();		
		int currPageCount = (accountLines - currPage*PAGE_SIZE)%5;
		for (int i = currPage*PAGE_SIZE; i!=currPageCount; ++i) {
			pageList.add(AccountList.accountList.get(i));
			//System.out.println(AccountList.accountList.get(i).getAccount_id() + "           " + i);
		}
		session.setAttribute("pageList", pageList);
		int pages = 0;// 总页数
		if (accountLines % PAGE_SIZE == 0) {
			// 对总页数赋值
			pages = accountLines / PAGE_SIZE;
		} else {
			// 对总页数赋值
			pages = accountLines / PAGE_SIZE + 1;
		}
		StringBuffer sb = new StringBuffer();//当前页
		StringBuffer last = new StringBuffer();
		StringBuffer next = new StringBuffer();
		if(currPage <= 1) {
			last.append("上一页");
		}
		else {
			last.append("<a href='FindUser?page=" + (currPage - 1) + "'>" + "上一页" + "</a>");
		}
		if(currPage < pages) {
			next.append("<a href='FindUser?page=" + (currPage + 1) + "'>" + "下一页" + "</a>");
		}
		else {
			next.append("下一页");
		}		
		for (int i = 1; i <= pages; i++) {// 通过循环构建分页条			
			if (i == currPage) {// 判断是否为当前页				
				sb.append("『" + i + "』");// 构建分页条
			} 
		}
		sb.append("/");
		sb.append("『" +pages + "』");		
		// 将分页条的字符串放置到request之中
		request.setAttribute("limitpage", pages);
		request.setAttribute("bar", sb.toString());
		request.setAttribute("last", last.toString());
		request.setAttribute("next", next.toString());		
		request.getRequestDispatcher("daohang6.jsp?page="+currPage).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
