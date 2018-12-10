package thread.jdkThreadTool.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	
	private static final int THREAD_CONT=30;
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CONT);
	private static Semaphore s = new Semaphore(10);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < THREAD_CONT; i++) {
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						s.acquire();
						
						System.out.println("save data");
						s.release();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
		}
		threadPool.shutdown();
	}

}
