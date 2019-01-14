package thread.jdkThreadTool.join;
/***
 * Thread中，join（）方法的作用是调用线程等待该线程完成后，才能继续用下运行。
 * @author victor
 *
 */
public class JoinTest {
//main线程要等到t1线程运行结束后，才会输出“main end”。如果不加t1.join(),main线程和t1线程是并行的。而加上t1.join(),程序就变成是顺序执行了
	public static void main0(String[] args) throws InterruptedException
    {
        System.out.println("main start");

        Thread t1 = new Thread(new Worker("thread-1"));
        t1.start();
        t1.join();
        System.out.println("main end");
    }
	
	//我们在用到join（）的时候，通常都是main线程等到其他多个线程执行完毕后再继续执行。其他多个线程之间并不需要互相等待。
	//下面这段代码并没有实现让其他线程并发执行，线程是顺序执行的。
	public static void main2(String[] args) throws InterruptedException
	{
	    System.out.println("main start");

	    Thread t1 = new Thread(new Worker("thread-1"));
	    Thread t2 = new Thread(new Worker("thread-2"));
	    t1.start();
	    //等待t1结束，这时候t2线程并未启动
	    t1.join();
	    
	    //t1结束后，启动t2线程
	    t2.start();
	    //等待t2结束
	    t2.join();

	    System.out.println("main end");
	}
	
	//t1、t2线程并行
	  public static void main(String[] args) throws InterruptedException{
	        System.out.println("main start");

	        Thread t1 = new Thread(new Worker("thread-1"));
	        Thread t2 = new Thread(new Worker("thread-2"));
	        
	        t1.start();
	        t2.start();
	        
	        t1.join();
	        t2.join();

	        System.out.println("main end");
	    }
}


class Worker implements Runnable
{

    private String name;

    public Worker(String name)
    {
        this.name = name;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            try
            {
                Thread.sleep(1l);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(name);
        }
    }

}