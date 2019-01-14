package thread.jdkThreadTool.threadLocal;

import java.util.concurrent.TimeUnit;

/***
 * 线程变量，一个以ThreadLocal对象为键，任务对象为值的存储结构
 * 通过set(T)方法来设置一个值   在当前线程下再通过get()方法获取到原先设置的值
 * @author victor
 *
 */
public class Profiler {
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {

		@Override
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
		
	};
	
	public static final void begin() {
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}
	
	public static final Long end() {
		return System.currentTimeMillis()-TIME_THREADLOCAL.get();
	}
	
	//复用在方法调用耗时统计功能
	public static void main(String[] args) throws InterruptedException {
		Profiler.begin();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Cost:" + Profiler.end()+" mills");
	}

}
