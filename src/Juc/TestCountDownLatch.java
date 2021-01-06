package Juc;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: ContDownLatch是一个同步辅助类，在完成某些运算时，只有其他所有线程的运算全部完成，
 * 当前运算才继续执行，这就叫闭锁。
 * @Author: liaocongcong
 * @Date: 2021/1/4 10:54
 */
public class TestCountDownLatch {
	public static void main(String[] args) throws InterruptedException {
		final  CountDownLatch latch = new CountDownLatch(10);//有多少个线程就这个参数就是几
		LatchDemo Id = new LatchDemo(latch);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			new Thread(Id).start();
			//Thread.sleep(200);
		}
		try {
			latch.await();//这10个线程执行完之前先等待
			System.out.println("这10个线程执行完之前先等待");
		}catch (InterruptedException e){

		}
		long end  = System.currentTimeMillis();
		System.out.println("耗费时间为 :"+(end-start)+"秒");
	}
}

class LatchDemo implements Runnable{

	private CountDownLatch latch;

	/*public LatchDemo(){

	}*/

	public LatchDemo(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		synchronized (this){
			try {
				for (int i = 0; i < 5000; i++) {
					if (i%2==0){
						System.out.println(Thread.currentThread().getName() +" :"+i);
					}
				}
			}finally {
				latch.countDown(); //每次执行完成一个就递减一个
			}
		}
	}
}
