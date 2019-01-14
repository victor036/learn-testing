package thread.jdkThreadTool.concurrent.waitNotify;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.LinkedList;
/**
 * 使用等待超时模式来构造一个简单的数据库连接池
 * 模拟从连接池中获取、使用、和释放连接的过程
 * @author victor
 *
 */
public class ConnectionPool {
	//双向队列维护连接
	private LinkedList<Connection> pool =new LinkedList<Connection>();
	
	
	
	public ConnectionPool(int initialSize) {
		if(initialSize >0) {
			for (int i = 0; i < initialSize; i++) {
				pool.addLast(ConnectionDriver.createConnect());
			}
		}
	}

	public void releaseConnection(Connection connection) {
		if(connection!=null) {
			synchronized (pool) {
				//连接释放后进行通知  这样其它消费者能够感知到连接池中已经归还了一个连接
				pool.addLast(connection);
				pool.notifyAll();
			}
		}
	}

	public Connection fetchConnection(long mills) throws InterruptedException{
		synchronized (pool) {
			//完全超时
			if(mills<=0) {
				while(pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			}else {
				long future = System.currentTimeMillis()+mills;
				long remaining = mills;
				while((pool.isEmpty() && remaining >0)) {
					pool.wait(remaining);
					remaining = future - System.currentTimeMillis();
				}
				Connection result =null;
				if(!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result; 
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
}
