package Juc;

import java.util.concurrent.CyclicBarrier;

/**
 * @Description:
 *
 * N个线程相互等待，任何一个线程完成之前，所有的线程都必须等待。
 *
 * 两种构建方法：CyclicBarrier（int parties）；CyclicBarrier（int parties，Runnable barrierAction）
 * @Author: liaocongcong
 * @Date: 2021/1/4 16:58
 */
public class CyclicBarrieDemo {

	public static void main(String[] args) {
		CyclicBarrier cbr = new CyclicBarrier(3);

		for (int i = 1; i <= cbr.getParties(); i++) {
			int tmp = i;
			new Thread(() -> {
				try {
					System.out.println("学员1号:翻越第"+tmp+"号障碍");
					//等待 让cbr中的所有线程都完成后才会继续下一步行动
					cbr.await();//即在所有人翻越完第一个障碍之前都不能继续翻越其他障碍
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			new Thread(() -> {
				try {
					System.out.println("学员2号:翻越第"+tmp+"号障碍");
					cbr.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			new Thread(() -> {
				try {
					System.out.println("学员3号:翻越第"+tmp+"号障碍");
					cbr.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
		System.out.println("main主线程finish");
	}
}