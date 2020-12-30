package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/29 14:24
 */
public class DeadLockTest {
	public static final Lock lock1 = new ReentrantLock();
	public static final Lock lock2 = new ReentrantLock();

	public static void main(String[] args) {
		DeadLockTest test = new DeadLockTest();
		Worker worker1 = test.new Worker(lock1, lock2);
		Worker worker2 = test.new Worker(lock2, lock1);
		worker1.start();
		worker2.start();
	}

	class Worker extends Thread {
		public Lock lock1;
		public Lock lock2;

		public Worker(Lock lock1, Lock lock2) {
			this.lock1 = lock1;
			this.lock2 = lock2;
		}


		@Override
		public void run() {
			try {
				if (lock1.tryLock(2, TimeUnit.SECONDS)) {
					try {
						System.out.println(Thread.currentThread().getName() + "获取了锁：" + lock1);
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (lock2.tryLock(4, TimeUnit.SECONDS)) {
							try {
								System.out.println(Thread.currentThread().getName() + "获取了锁：" + lock2);
							} finally {
								lock2.unlock();
								System.out.println(Thread.currentThread().getName() + "解锁：" + lock2);
							}
						} else {
							System.out.println("获取锁失败：" + lock2);
						}
					} finally {
						lock1.unlock();
						System.out.println(Thread.currentThread().getName() + "解锁：" + lock1);
					}
				} else {
					System.out.println("获取锁失败：" + lock1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
