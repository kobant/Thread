package Executors;



import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/30 18:00
 */
public class ThreadPoolTest {
	public static void main(String[] args) {

		//创建队列
		BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(20);

		ThreadPoolExecutor pool = new ThreadPoolExecutor(3,5,50, TimeUnit.MILLISECONDS,blockingQueue);
		//创建七个任务
		Runnable t1 = new MyThread();
		Runnable t2 = new MyThread();
		Runnable t3 = new MyThread();
		Runnable t4 = new MyThread();
		Runnable t5 = new MyThread();
		Runnable t6 = new MyThread();
		Runnable t7 = new MyThread();
		//每个任务会在一个线程上执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		pool.execute(t6);
		pool.execute(t7);
		//关闭线程池
		pool.shutdown();


		/**
		 * 根据ThreadPoolExecutor源码前面大段的注释，我们可以看出，当试图通过excute方法将一个Runnable任务添加到线程池中时，按照如下顺序来处理：
		 *     1、如果线程池中的线程数量少于corePoolSize，即使线程池中有空闲线程，也会创建一个新的线程来执行新添加的任务；
		 *     2、如果线程池中的线程数量大于等于corePoolSize，但缓冲队列workQueue未满，则将新添加的任务放到workQueue中，
		 *     按照FIFO的原则依次等待执行（线程池中有线程空闲出来后依次将缓冲队列中的任务交付给空闲的线程执行）；
		 *
		 *    3、如果线程池中的线程数量大于等于corePoolSize，且缓冲队列workQueue已满，但线程池中的线程数量小于maximumPoolSize，
		 *    则会创建新的线程来处理被添加的任务；
		 *
		 *   4、如果线程池中的线程数量等于了maximumPoolSize，有4种处理方式（该构造方法调用了含有5个参数的构造方法，
		 *   并将最后一个构造方法为RejectedExecutionHandler类型，它在处理线程溢出时有4种方式，这里不再细说，要了解的，自己可以阅读下源码）。
		 *
		 *     总结起来，也即是说，当有新的任务要处理时，先看线程池中的线程数量是否大于corePoolSize，再看缓冲队列workQueue是否满，
		 *     最后看线程池中的线程数量是否大于maximumPoolSize。
		 *
		 *     另外，当线程池中的线程数量大于corePoolSize时，如果里面有线程的空闲时间超过了keepAliveTime，就将其移除线程池，这样，
		 *     可以动态地调整线程池中线程的数量。
		 */


		/**
		 *  下面说说几种排队的策略：
		 *
		 *     1、直接提交。缓冲队列采用 SynchronousQueue，它将任务直接交给线程处理而不保持它们。如果不存在可用于立即运行任务的线程
		 *     （即线程池中的线程都在工作），则试图把任务加入缓冲队列将会失败，因此会构造一个新的线程来处理新添加的任务，
		 *     并将其加入到线程池中。直接提交通常要求无界 maximumPoolSizes（Integer.MAX_VALUE） 以避免拒绝新提交的任务。
		 *     newCachedThreadPool采用的便是这种策略。
		 *
		 *     2、无界队列。使用无界队列（典型的便是采用预定义容量的 LinkedBlockingQueue，理论上是该缓冲队列可以对无限多的任务排队）
		 *     将导致在所有 corePoolSize 线程都工作的情况下将新任务加入到缓冲队列中。这样，创建的线程就不会超过 corePoolSize，
		 *     也因此，maximumPoolSize 的值也就无效了。当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列。
		 *     newFixedThreadPool采用的便是这种策略。
		 *
		 *     3、有界队列。当使用有限的 maximumPoolSizes 时，有界队列（一般缓冲队列使用ArrayBlockingQueue，并制定队列的最大长度）
		 *     有助于防止资源耗尽，但是可能较难调整和控制，队列大小和最大池大小需要相互折衷，需要设定合理的参数。
		 */
	}
}

class MyThread implements Runnable{

	@Override
	public void run() {

		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
		try{
			Thread.sleep(100);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
