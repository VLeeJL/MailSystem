package com.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {
	private static Socket socket;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		try {
			Scanner sc = new Scanner(System.in);
			Socket socket=new Socket("localhost", 27);
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String info=br.readLine();

			System.out.println(info);
			
			PrintStream ps = new PrintStream(socket.getOutputStream());
			String tmp = sc.nextLine();
			while(!tmp.equals("quit"))
			{
				ps.println(tmp);
				info = br.readLine();
				System.out.println(info);
				tmp = sc.nextLine();
			}
			ps.close();
			br.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
