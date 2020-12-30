package Juc;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/30 11:18
 */


/**
 * 二、volatile 关键字-内存可见性
 * 1、概念：内存可见性
 *
 *     内存可见性（Memory Visibility）是指当某个线程正在使用对象状态而另一个线程在同时修改该状态，需要确保当一个线程修改了对象状态后，
 *     其他线程能够看到发生的状态变化。
 *     可见性错误是指当读操作与写操作在不同的线程中执行时，我们无法确保执行读操作的线程能实时地看到其他线程写入的值，有时甚至是根本不可能的事情。
 *     我们可以通过同步来保证对象被安全地发布。除此之外我们也可以使用一种更加轻量级的volatile 变量。
 */

/**
 * 2、问题描述：
 *
 *     这里有两个线程，一个main线程，一个ThreadDemo线程；
 *     main启动后，又启动了ThreadDemo；
 *     ThreadDemo先睡了一会，再把flag的值由false改为true；
 *     main读到flag的值，继续进行操作。
 */

public class TestVolatile {

	public static void main(String[] args) {

		ThreadDemo threadDemo = new ThreadDemo();
		new Thread(threadDemo).start();

		while (true){
			if (threadDemo.isFlag()){
				System.out.println("------------------");
				break;
			}
		}
	}

}

class ThreadDemo implements Runnable{

	private volatile boolean flag = false;
	@Override
	public void run() {
		try {
			Thread.sleep(200);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		flag = true;
		System.out.println("flag ="+isFlag());
	}
	public boolean isFlag(){
		return flag;
	}
}
