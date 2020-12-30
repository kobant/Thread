package com.company;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/27 15:25
 */

//测试线程优先级
public class TestSleep {

	public static void main(String[] args) {
		//主线程默认
		System.out.println(Thread.currentThread().getName() + "---->"+Thread.currentThread().getPriority());

		Mypriority mypriority = new Mypriority();

		Thread t1 = new Thread(mypriority);
		Thread t2 = new Thread(mypriority);
		Thread t3 = new Thread(mypriority);
		Thread t4 = new Thread(mypriority);
		Thread t5 = new Thread(mypriority);
		Thread t6 = new Thread(mypriority);

		//设置线程优先级
		t1.start();
		t2.setPriority(1);
		t2.start();

		t3.setPriority(4);
		t3.start();

		t4.setPriority(Thread.MAX_PRIORITY);
		t4.start();

		t5.setPriority(3);
		t5.start();

		t6.setPriority(-1);
		t6.start();

	}

}


class Mypriority implements Runnable{

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "---->"+Thread.currentThread().getPriority());
	}
}