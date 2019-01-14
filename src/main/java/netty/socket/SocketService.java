package netty.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;

public class SocketService {
	ServerSocket serverSocket;

	public SocketService() {
		try {
			serverSocket = new ServerSocket(8081);
			while (true) {
				Socket socket = serverSocket.accept();
				SocketServiceThread sst = new SocketServiceThread(socket);
				sst.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class SocketServiceThread extends Thread {
		Socket socket;
		ObjectInputStream in;
		ObjectOutputStream out;
		boolean runFlag = true;

		public SocketServiceThread(Socket socket) {
			if (null == socket) {
				runFlag = false;
				return;
			}
			this.socket = socket;
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			if (null == socket) {
				System.out.println("socket is null");
				return;
			}
			try {
				in = new ObjectInputStream(socket.getInputStream());
				while (runFlag) {
					if (socket.isClosed()) {
						System.out.println("socket is closed");
						return;
					}
					try {
						String obj = (String) in.readObject();
						if (obj instanceof String) {
							System.out.println("Server recive:" + obj);
							Date date = new Date();
							out.writeObject("[" + date + "]" + obj);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							out.flush();
						} else {
							System.out.println("Server recive:" + obj);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SocketException e) {
						e.printStackTrace();
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			} catch (Exception e) {
				return;
			}
		}

		public void exit() {
			runFlag = false;
		}
	}

	public static void main(String[] args) {
		System.out.println("===============start service===============");
		new SocketService();
	}

	/*
	 * public static void main(String[] args) { // TODO Auto-generated method stub
	 * try { SocketAddress address = new InetSocketAddress("127.0.0.1", 30001); //
	 * 启动监听端口 8001 ServerSocket ss = new ServerSocket(); ss.bind(address); // 接收请求
	 * Socket s = ss.accept(); new Thread(new T(s)).start(); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 * 
	 * static class T implements Runnable { public void run() { try {
	 * System.out.println(socket.toString()); socket.setKeepAlive(true);
	 * socket.setSoTimeout(5 * 1000); String _pattern = "yyyy-MM-dd HH:mm:ss";
	 * SimpleDateFormat format = new SimpleDateFormat(_pattern); while (true) {
	 * System.out.println("开始：" + format.format(new Date())); try { InputStream ips
	 * = socket.getInputStream(); ByteArrayOutputStream bops = new
	 * ByteArrayOutputStream(); int data = -1; while ((data = ips.read()) != -1) {
	 * System.out.println(data); bops.write(data); }
	 * System.out.println(Arrays.toString(bops.toByteArray())); } catch
	 * (SocketTimeoutException e) { e.printStackTrace(); } catch (SocketException e)
	 * { e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
	 * Thread.sleep(1000); System.out.println(socket.isBound()); // 是否邦定
	 * System.out.println(socket.isClosed()); // 是否关闭
	 * System.out.println(socket.isConnected()); // 是否连接
	 * System.out.println(socket.isInputShutdown()); // 是否关闭输入流
	 * System.out.println(socket.isOutputShutdown()); // 是否关闭输出流
	 * System.out.println("结束：" + format.format(new Date())); } } catch (Exception
	 * e) { e.printStackTrace(); } }
	 * 
	 * private Socket socket = null;
	 * 
	 * public T(Socket socket) { this.socket = socket; }
	 * 
	 * public Socket getSocket() { return socket; }
	 * 
	 * public void setSocket(Socket socket) { this.socket = socket; } }
	 */
}
