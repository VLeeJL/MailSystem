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
    	request.setCharacterEncoding("utf-8");//ҳ�汣֤��������ҳ�����������PAGE_SIZE
		HttpSession session=request.getSession();
		int accountLines = AccountList.accountList.size();// ��ѯ�ܼ�¼��
		int currPage = 0;
		if (request.getParameter("page") != null) {//�ж��Ƿ��ǵ�һ�η���ҳ�棬���ǵ�һ����Ϊnull			
			currPage = Integer.parseInt(request.getParameter("page"));// �Ե�ǰҳ�븳ֵ			
		}	
		ArrayList<Account> pageList = new ArrayList<Account>();		
		int currPageCount = (accountLines - currPage*PAGE_SIZE)%5;
		for (int i = currPage*PAGE_SIZE; i!=currPageCount; ++i) {
			pageList.add(AccountList.accountList.get(i));
			//System.out.println(AccountList.accountList.get(i).getAccount_id() + "           " + i);
		}
		session.setAttribute("pageList", pageList);
		int pages = 0;// ��ҳ��
		if (accountLines % PAGE_SIZE == 0) {
			// ����ҳ����ֵ
			pages = accountLines / PAGE_SIZE;
		} else {
			// ����ҳ����ֵ
			pages = accountLines / PAGE_SIZE + 1;
		}
		StringBuffer sb = new StringBuffer();//��ǰҳ
		StringBuffer last = new StringBuffer();
		StringBuffer next = new StringBuffer();
		if(currPage <= 1) {
			last.append("��һҳ");
		}
		else {
			last.append("<a href='FindUser?page=" + (currPage - 1) + "'>" + "��һҳ" + "</a>");
		}
		if(currPage < pages) {
			next.append("<a href='FindUser?page=" + (currPage + 1) + "'>" + "��һҳ" + "</a>");
		}
		else {
			next.append("��һҳ");
		}		
		for (int i = 1; i <= pages; i++) {// ͨ��ѭ��������ҳ��			
			if (i == currPage) {// �ж��Ƿ�Ϊ��ǰҳ				
				sb.append("��" + i + "��");// ������ҳ��
			} 
		}
		sb.append("/");
		sb.append("��" +pages + "��");		
		// ����ҳ�����ַ������õ�request֮��
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
