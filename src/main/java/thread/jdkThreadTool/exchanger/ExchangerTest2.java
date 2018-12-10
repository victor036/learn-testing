package thread.jdkThreadTool.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * http://lixuanbin.iteye.com/blog/2166772
 * 有两个任务类，一个递增地产生整数，一个产生整数0，然后双方进行交易
输出结果验证了以下两件事情：
	exchange方法真的帮一对线程交换了数据；
	exchange方法真的会阻塞调用方线程直至另一方线程参与交易。
 * @author victor
 *
 */
public class ExchangerTest2 {
	private static volatile boolean isDone = false;

	static class ExchangerProducer implements Runnable {
		private Exchanger<Integer> exchanger;
		private static int data = 1;

		ExchangerProducer(Exchanger<Integer> exchanger) {
			this.exchanger = exchanger;
		//	new LinkedTransferQueue<>(arg0)
		}

		@Override
		public void run() {
			while (!Thread.interrupted() && !isDone) {
				for (int i = 1; i <= 3; i++) {
					try {
						TimeUnit.SECONDS.sleep(1);
						data = i;
						System.out.println("producer before: " + data);
						data = exchanger.exchange(data);
						System.out.println("producer after: " + data);
					} catch (InterruptedException e) {
						e.printStackTrace();
						// log.error(e, e);
					}
				}
				isDone = true;
			}
		}
	}

	static class ExchangerConsumer implements Runnable {
		private Exchanger<Integer> exchanger;
		private static int data = 0;

		ExchangerConsumer(Exchanger<Integer> exchanger) {
			this.exchanger = exchanger;
		}

		@Override
		public void run() {
			while (!Thread.interrupted() && !isDone) {
				data = 0;
				System.out.println("consumer before : " + data);
				try {
					TimeUnit.SECONDS.sleep(1);
					data = exchanger.exchange(data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("consumer after : " + data);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		Exchanger<Integer> exchanger = new Exchanger<Integer>();
		ExchangerProducer producer = new ExchangerProducer(exchanger);
		ExchangerConsumer consumer = new ExchangerConsumer(exchanger);
		exec.execute(producer);
		exec.execute(consumer);
		exec.shutdown();
		try {
			exec.awaitTermination(30, TimeUnit.SECONDS);
			System.out.println("await 30");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
