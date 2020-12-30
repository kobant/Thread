package Juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/30 14:16
 */

/**
 * 2、问题：i++的原子性问题
 *
 * i++的操作实际上分为三个步骤“读-改-写”，eg：int temp = i; i = i + 1; i = temp;
 */
public class TestAtomicDemo {
	public static void main(String[] args) {
		AtomicDemo ad = new AtomicDemo();

		for (int i = 0; i < 30; i++) {
			new Thread(ad).start();
		}
	}
}

class AtomicDemo implements Runnable{
	private AtomicInteger serialNumber = new AtomicInteger(0);


	@Override
	public void run() {
		try { Thread.sleep(3000); } catch (InterruptedException e) {}
		System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
	}

	public int getSerialNumber(){ return serialNumber.getAndIncrement();}
}