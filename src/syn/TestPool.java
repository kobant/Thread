package syn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/27 20:27
 */
//测试线程池
public class TestPool {

	public static void main(String[] args) {
		//1.创建服务，创建线程池
		ExecutorService service = Executors.newFixedThreadPool(10);

		//执行
		service.execute(new MyThread());
		service.execute(new MyThread());
		service.execute(new MyThread());
		service.execute(new MyThread());

		//2.关闭连接
		service.shutdown();
	}

}


class MyThread implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}
}