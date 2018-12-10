package thread.jdkThreadTool.countdownlatch;

public class JoinCountDownLatchTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread parser1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("parser1 finish");
			}
		});
		
		Thread parser2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("parser2 finish");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		parser1.start();
		parser2.start();
		//join 用于让当前执行线程等待join线程执行结束
		parser1.join();
		parser2.join();
		System.out.println("all parser finish");
	}

}
