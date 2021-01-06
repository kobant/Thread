package Juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2021/1/4 15:39
 */
public class TestNewCachedThreadPool {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i <= 10; i++) {
			final int index = i;
			try {
				Thread.sleep(index*1000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					String threadName = Thread.currentThread().getName();
					System.out.println("执行：" + index + "，线程名称：" + threadName);
				}
			});
		}
	}
}
