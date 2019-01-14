package thread.jdkThreadTool.concurrent.waitNotify;

import org.omg.Messaging.SyncScopeHelper;

public class WaitNotify_Demo1 {
	//等待超时模式
	public static void main(String[] args) throws InterruptedException {
		
	}
	
	//对当前对象加锁
//	public synchronized Object get(long mills) throws InterruptedException{
//		long future = System.currentTimeMillis()+mills;
//		long remaining = mills;
//		//当超时大于0返回值不满足要求
//		while((result== null) && remaining >0) {
//			wait(remaining);
//			remaining = future-System.currentTimeMillis();
//		}
//		return result ;
//		
//	}
}
