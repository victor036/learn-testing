package netty.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Client(){
		try {
			socket=new Socket("localhost",8081);
			out=new ObjectOutputStream(socket.getOutputStream());
			ReadThread readThread=new ReadThread();
			readThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg){
		System.out.println("send message:"+msg);
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class ReadThread extends Thread{
		boolean runFlag=true;
		public void run(){
			try {
				in=new ObjectInputStream(socket.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while(runFlag){
				if(socket.isClosed()){
					return;
				}
				try {
					Object obj=in.readObject();
					if(obj instanceof String){
						System.out.println("Client recive:"+obj);
					}
				} 
				catch (IOException e) {
					e.printStackTrace();
				} 
				catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void exit(){
			runFlag=false;
		}
	}
	
	public static void main(String[] args) {
		Client socketClient=new Client();
		System.out.println("build socketClient");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		socketClient.sendMessage("Hello first.");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		socketClient.sendMessage("Hello second.");
	}
	
}
