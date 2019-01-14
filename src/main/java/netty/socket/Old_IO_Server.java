package netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
/***
 * 传统socket服务端
 */
public class Old_IO_Server {
    public static void main(String[] args) throws IOException {
		//创建Socket服务，监听10010端口
    	ServerSocket server = new ServerSocket(10010);
    	System.out.println("服务端启动！");
    	while(true){
    		//获取一个套接字(阻塞)
    		final Socket socket = server.accept();
    		System.out.println("出现一个新客户端！");
    		//业务处理
    		handler(socket);
    	}
	}
 
    /**
     * 读取数据
     * @param socket
     * @throws Exception 
     * */
	private static void handler(Socket socket){
		try {
			byte [] bytes = new byte[1024];
			InputStream input = socket.getInputStream();
			
			int read = 0;
			while(read!=-1){
				//读取数据(阻塞)
				read = input.read(bytes);
				System.out.println(new String(bytes,0,read));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				System.out.println("socket 关闭");
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
