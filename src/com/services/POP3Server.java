package com.services;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.entity.FilterList;
import com.entity.ServerConfig;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
public class POP3Server
{
    private static ServerSocket serverSocket;
    private static List<POP3Session> socketList= Collections.synchronizedList(new LinkedList<POP3Session>());
    private static Pop3Thread pop3Thread=null;  
    private boolean pop3Flag = true;
    
    public void start() {
		this.pop3Flag = true;
		open();		
	}

	public void stop() {
		this.pop3Flag = false;
		close();
	}

    private class Pop3Thread extends  Thread
    {
        @Override
        public void run()
        {
        	if (!pop3Flag) {
        		System.out.println("POP3 server没有启动");
        		return;
        	}
            try
            {
                serverSocket=new ServerSocket(1010);//ServerConfig.pop3_port
                System.out.println("POP3 seerver is listening");
                while(true)
                {
                    Socket socket=serverSocket.accept();
                    if (!FilterList.filterIPAddresList.contains(socket.getInetAddress().getHostAddress())) {//拦截ip地址
                    	POP3Session POP3Session=new POP3Session(socket);
                        POP3Session.start();
                        socketList.add(POP3Session);
                    }
                    else socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeSocket(POP3Session POP3Session)
    {
        if(POP3Session!=null)
        {
            POP3Session.releaseSocket();
            socketList.remove(POP3Session);
        }
    }

  public void close()
  {
      try {
          serverSocket.close();
          for(POP3Session POP3Session:socketList)
          {
              POP3Session.releaseSocket();
          }
          socketList.clear();
      } catch (IOException e) {
          e.printStackTrace();
      }
      pop3Thread=null;
  }

  public boolean open()
  {
      if(pop3Thread!=null) return false;
      (pop3Thread=new Pop3Thread()).start();
      return true;
  }
}
