package com.services;


import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.dao.MailDao;
import com.entity.Account;
import com.entity.MailContent;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
public class POP3Session extends Thread //Thread
{
    //各个状态
    private static final int INIT=0;
    private static final int USER=1;
    private static final int PASS_SUCCESS=2;
    private static final String ERROR_UNKNOWN_MESSAGE="-ERR Unknown command ";
    private static final String FAIL_LOGIN_MESSAGE="-ERR Fail To Login";
    private static final String OK_MESSAGE="+OK core mail";
    private static final String WELCOME_MESSAGE="+OK Welcome to Mail Pop3 Server";
    private static String DOMAIN;//域名
    private static final String hh="\r\n";


    private ArrayList<MailContent> recvMailList = null;
    private List<Long> listDeleteFlag=new LinkedList<>();
    private int state=INIT;
    private Account user=new Account();
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter ps;
    public POP3Session(Socket s) {
    	socket=s;
    	try {
            socket.setSoTimeout(30000);
            ps=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true);
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        ps.println(WELCOME_MESSAGE);
        String content=null;
        while((content=readFromClient())!=null)
        {
            String temp=content.toLowerCase();
            if(temp.equals("quit"))
            {
                closeSocket();
            }
            else if(state==INIT&&temp.startsWith("user "))
            {
                try {
					handleINITState(content);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if(state==USER&&temp.startsWith("pass "))
            {
                try {
					handleUSERState(content);
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else if(state==PASS_SUCCESS)
            {
                handlePASSState(content);
            }
            else
            {
                errorMessage(content);
            }
        }
    }

    private void handleINITState(String str) throws SQLException
    {
        String[] strList=splitString(str);
        if(strList[0].equals("user")&&!strList[1].equals("")&&AccountService.queryAccountName(strList[1]))
        {
            state=USER;
            user.setAccount_name(strList[1]);
            ps.println(OK_MESSAGE);
        }
        else errorMessage(str);
    }

    private void handleUSERState(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException
    {
        String[] strList=splitString(str);
        if(strList[0].equals("pass")&&!strList[1].equals("")&&AccountService.loginAccount(user.getAccount_name(), strList[1]))
        {
            user.setPassword(strList[1]);
            state=PASS_SUCCESS;
            Account account = AccountService.getAccount(user.getAccount_name());
            user.updateAccount(account.getAccount_id(), user.getAccount_name(), account.getPassword(), account.getAuthority(), account.getDisabled());
            STAT();
        }
        else
        {
            user=new Account();
            state=INIT;
            ps.println(FAIL_LOGIN_MESSAGE);
        }

    }

    private void handlePASSState(String str)
    {
        String temp=str.toLowerCase();
        if(temp.equals("stat"))
        {
            STAT();
            return;
        }
        else if(temp.equals("noop"))
        {
            return ;
        }
        else if(temp.equals("rset"))
        {
            listDeleteFlag.clear();
            ps.println(OK_MESSAGE);
            return;
        }
        try
        {
            String[] strList=splitString(str);
            System.out.println(str + ":   " + strList[0]);
            if(strList==null||strList[1].equals("")) {
                errorMessage(str);
                return;
            }
            if(strList[0].equals("list"))
            {
                LIST(Integer.parseInt(strList[1]));
            }
            else if(strList[0].equals("retr"))
            {
                RETR(Integer.parseInt(strList[1]));
            }
            else if(strList[0].equals("dele"))
            {
                DELE(strList);
            }
            else if(strList[0].equals("delemailbyid")) {
            	DELE_MAIL_ID(strList);
            }
            else errorMessage(str);
        } catch (Exception e)
        {
            errorMessage(str);
        }

    }
    
    private void DELE_MAIL_ID(String[] strList) {  
    	listDeleteFlag.add(Long.valueOf(strList[1]));
        ps.println(OK_MESSAGE);
    }

    private void DELE(String[] strList)
    {
        int number=Integer.parseInt(strList[1]);
        int cnt = recvMailList.size();
        if(number>cnt)
        {
            errorMessage("-ERR Unknown Message");
        }
        else
        {
            listDeleteFlag.add(recvMailList.get(number).getMail_id());
            ps.println(OK_MESSAGE);
        }
    }

    //用户退出回话
    private void closeSocket()
    {
        ps.println(OK_MESSAGE);
        //如果已登录，可能要删除某些邮件
        if(state==PASS_SUCCESS)
        {
            if(listDeleteFlag.size()>0)
            {            	
            	for(Long i : listDeleteFlag)
                {
            		try {
						MailService.deleteMail(i);
						System.out.println("垃圾邮件箱不为空" + i);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
            else
            	System.out.println("垃圾邮件箱为空");
        }
        releaseSocket();
        POP3Server.removeSocket(this);
    }

    public void releaseSocket()
    {
        try {
            socket.close();
            bufferedReader.close();
            ps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取用户的输入
    private String readFromClient()
    {
        try
        {
            return bufferedReader.readLine();
        } catch (IOException e) {
            closeSocket();
        }
        return null;
    }

    //输出错误信息
    private void errorMessage(String str)
    {
        String temp=str.toLowerCase();
        ps.println(ERROR_UNKNOWN_MESSAGE+str);
        if(state==USER) state=INIT;
    }

    //分割用户的输入，用户密码可能包含空格
    private String[] splitString(String content)
    {
        String[] strList=new String[2];
        try
        {
            strList[0]=content.substring(0,content.indexOf(' '));
            strList[1]=content.substring(content.indexOf(' ')+1,content.length());
            strList[0]=strList[0].toLowerCase();
            return strList;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void setDOMAIN(String str)
    {
        DOMAIN=str;
    }
    
    private void STAT()
    {    	
		try {
			recvMailList = MailDao.getMailContext(user.getAccount_name());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int cnt=0;
        for(MailContent email : recvMailList)
        {
            String str = email.getContext();
            cnt+=str.getBytes().length;
        }
        ps.println("+OK "+recvMailList.size()+ " messages "+cnt+"Bytes");
    }

    private void LIST(int number)
    {
    	MailContent email = recvMailList.get(number);
        String str=getEmailString(email);
        ps.println(OK_MESSAGE+" "+number+" "+str.getBytes().length+"Bytes");
    }

    private void RETR(int number)
    {
    	MailContent email = recvMailList.get(number);
        String str=getEmailString(email);
        ps.println(OK_MESSAGE);
        ps.println(str);
    }
    
    private String getEmailString(MailContent email)
    {
        String str=new String();
        str+="MAIL_ID: "+email.getMail_id()+hh;
        str+="FROM: "+email.getSendAccount()+hh;
        str+="SUBJECT: "+email.getSubject()+hh;
        str+="DATE: "+email.getSendTime()+hh;
        str+=hh;
        str+=email.getContext()+hh+"."+hh;
        return str;
    }

}
