package Juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description:  //信号量
 * //需求：模拟抢车位游戏，6辆汽车抢占3个车位
 *
 * semaphore.acquire()得到了资源，那些没有抢占到资源的线程进入等到状态
 *
 * semaphore.release()释放资源，会将当前的信号量释放，然后唤醒等待的线程
 * @Author: liaocongcong
 * @Date: 2021/1/5 16:35
 */
public class JavaMess {
	public static void main(String[] args) {

		Semaphore semaphore = new Semaphore(3);
		for (int i = 0; i <= 6; i++) {
			final int temp = i;
			new Thread(()->{
				try {
					//得到资源
					semaphore.acquire();
					System.out.println("汽车" + temp + "抢到了车位...");
					TimeUnit.SECONDS.sleep(2);
					System.out.println("汽车" + temp + "离开了车位...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					//释放资源
					//测试用户 liaocongcong
					semaphore.release();
				}
			},"汽车" + i).start();
		}
	}
}
