package thread.jdkThreadTool;

import java.util.Map.Entry;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BankWaterService implements Runnable{
	//创建4个屏障，处理完之后执行当前类的run方法
	private CyclicBarrier c= new CyclicBarrier(4,this);
	
	//假设只有4个sheet，所以只启动4个线程
	private Executor executor = Executors.newFixedThreadPool(4);
	
	private ConcurrentHashMap<String, Integer> sheetBankWaterCount =new ConcurrentHashMap<String, Integer> ();
	
	private void count() {
		for (int i = 0; i < 4; i++) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					//计算当前sheet的银流数据,计算代码略去
					sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
					//银流计算完成，插入一个屏障
					try {
						c.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
		
	}
	
	

	@Override
	public void run() {
		int result =0;
		//汇总每个sheet计算出的结果
		for (Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
			result+=sheet.getValue();
		}
		sheetBankWaterCount.put("result", result);
		System.out.println(result);
	}
	
	
	public static void main(String[] args) {
		BankWaterService bankWaterService= new BankWaterService();
		bankWaterService.count();

	}
}