package thread.jdkThreadTool.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * 线程间交换数据
 * 应用场景：
 * 	校对工作
 * @author victor
 *
 */
public class ExchangerTest1 {
	private static final Exchanger<String> exgr= new Exchanger<String>();
	
	private static ExecutorService threadPool =Executors.newFixedThreadPool(2);
	
	public static void main(String[] args) {
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String A ="银行流水A";
					exgr.exchange(A);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String B="银行流水B";
					String A =exgr.exchange(B);
					System.out.println(" A与B数据是否一致"+A.equals(B)
									+",A录入的是"+A+",B录入的是"+B);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		threadPool.shutdown();
	}
	
}
