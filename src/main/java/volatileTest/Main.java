package volatileTest;

import java.util.concurrent.atomic.AtomicInteger;
//关键字volatile可以用来修饰字段(成员变量)，就是告知程序任务对该变量的访问均需要从共享内存中获取
//而对它的改变必须同步刷新回共享内存，它能保证 所有线程对变量访问的可见性
/***
 * 大家想一下这段程序的输出结果是多少？也许有些朋友认为是10000。
 * 但是事实上运行它会发现每次运行结果都不一致，都是一个小于10000的数字。

可能有的朋友就会有疑问，不对啊，上面是对变量inc进行自增操作，由于volatile保证了可见性，
那么在每个线程中对inc自增完之后，在其他线程中都能看到修改后的值啊，所以有10个线程分别进行了1000次操作，
那么最终inc的值应该是1000*10=10000。

这里面就有一个误区了，volatile关键字能保证可见性没有错，但是上面的程序错在没能保证原子性。
可见性只能保证每次读取的是最新的值，但是volatile没办法保证对变量的操作的原子性。
 * 
 *通常来说，使用volatile必须具备以下2个条件：
1）对变量的写操作不依赖于当前值
2）该变量没有包含在具有其他变量的不变式中
 */
public class Main {
	public volatile int inc = 0;
//    public volatile AtomicInteger inc = new AtomicInteger(0);
 
    public void increase() {
        inc++;
        //inc.incrementAndGet();
    }
 
    public static void main(String[] args) {
        final Main test = new Main();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                };
            }.start();
        }
        while(Thread.activeCount()>1)  //保证前面的线程都执行完
            Thread.yield();
        System.out.println(test.inc);
//        sysout
    }
}

/**
这里面就有一个误区了，volatile关键字能保证可见性没有错，但是上面的程序错在没能保证原子性。可见性只能保证每次读取的是最新的值，但是volatile没办法保证对变量的操作的原子性。

在前面已经提到过，自增操作是不具备原子性的，它包括读取变量的原始值、进行加1操作、写入工作内存。那么就是说自增操作的三个子操作可能会分割开执行，就有可能导致下面这种情况出现：

假如某个时刻变量inc的值为10，

线程1对变量进行自增操作，线程1先读取了变量inc的原始值，然后线程1被阻塞了；

然后线程2对变量进行自增操作，线程2也去读取变量inc的原始值，由于线程1只是对变量inc进行读取操作，而没有对变量进行修改操作，所以不会导致线程2的工作内存中缓存变量inc的缓存行无效，也不会导致主存中的值刷新，所以线程2会直接去主存读取inc的值，发现inc的值时10，然后进行加1操作，并把11写入工作内存，最后写入主存。

然后线程1接着进行加1操作，由于已经读取了inc的值，注意此时在线程1的工作内存中inc的值仍然为10，所以线程1对inc进行加1操作后inc的值为11，然后将11写入工作内存，最后写入主存。

那么两个线程分别进行了一次自增操作后，inc只增加了1。

根源就在这里，自增操作不是原子性操作，而且volatile也无法保证对变量的任何操作都是原子性的。

解决方案：可以通过synchronized或lock，进行加锁，来保证操作的原子性。也可以通过AtomicInteger。

在java 1.5的java.util.concurrent.atomic包下提供了一些原子操作类，即对基本数据类型的 自增（加1操作），自减（减1操作）、以及加法操作（加一个数），减法操作（减一个数）进行了封装，保证这些操作是原子性操作。atomic是利用CAS来实现原子性操作的（Compare And Swap），CAS实际上是利用处理器提供的CMPXCHG指令实现的，而处理器执行CMPXCHG指令是一个原子性操作。

**/