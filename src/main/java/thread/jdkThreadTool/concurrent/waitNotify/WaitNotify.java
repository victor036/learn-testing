package thread.jdkThreadTool.concurrent.waitNotify;

import java.text.SimpleDateFormat;
import java.util.Date;


public class WaitNotify {

	static boolean flag = true;
	static Object lock = new Object();
	
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread waitThread = new Thread(new Wait(),"WaitThread");
		waitThread.start();
		Thread.sleep(1000);
		Thread notifyThread =  new Thread(new Notify(),"NotifyThread"); 
		notifyThread.start();
	}
	
	static class Wait implements Runnable{

		@Override
		public void run() {
			synchronized (lock) {
				//条件不满足 继续wait 同时释放了lock的锁
				while(flag) {
					try {
						System.out.println(Thread.currentThread()+" flag is true.wait@"+
								new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.wait();
						System.out.println(Thread.currentThread()+" flag is true.wait finish@"+
								new SimpleDateFormat("HH:mm:ss").format(new Date()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//条件满足，完成工作
				
				System.out.println(Thread.currentThread()+" flag is false.running@"+
						new SimpleDateFormat("HH:mm:ss").format(new Date()));
				
			}
			
		}
		
		
	}
	

	static class Notify implements Runnable{

		@Override
		public void run() {
			//加锁，拥用lock的Monitor
			synchronized (lock) {
				//条件不满足 继续wait 同时释放了lock的锁
				while(flag) {
					//获取lock的锁，然后进行通知 通知时不会释放lock的锁
					try {
						System.out.println(Thread.currentThread()+" hold lock .notify@"+
								new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.notifyAll();
						flag = false;
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			//再次加锁
			synchronized (lock) {
				System.out.println(Thread.currentThread()+" hold lock .notify again@"+
						new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
		
	}
}
