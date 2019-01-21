package thread.jdkThreadTool.future;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {

	public static void main(String[] args) {
//		使用Callable+Future获取执行结果
//		ExecutorService executor = Executors.newCachedThreadPool();
//        Task task = new Task();
//        Future<Integer> result = executor.submit(task);
//        executor.shutdown();
//		子线程在进行计算
//		主线程在执行任务
//		task运行结果4950
//		所有任务执行完毕
		
		
//		使用Callable+FutureTask获取执行结果
		ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        executor.submit(futureTask);
        executor.shutdown();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
         
        System.out.println("主线程在执行任务");
         
        try {
            System.out.println("task运行结果"+futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
         
        System.out.println("所有任务执行完毕");
	}

	//计算两个List<Integer>中的数的总和的时候就可以用Future模式提高效率
	public int getTotal(final List<Integer> a, final List<Integer> b) throws ExecutionException, InterruptedException {
	    Future<Integer> future = Executors.newCachedThreadPool().submit(new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            int r = 0;
	            for (int num : a) {
	                r += num;
	            }
	            return r;
	        }
	    });

	    int r = 0;
	    for (int num : b) {
	        r += num;
	    }
	    return r + future.get();
	}
}

class Task implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        Thread.sleep(3000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
            
        return sum;
    }
}