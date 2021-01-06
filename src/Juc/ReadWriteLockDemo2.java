package Juc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description:  使用读写锁
 * @Author: liaocongcong
 * @Date: 2021/1/5 16:46
 *
 *
 * 读类型线程与写类型线程共存问题：
 *
 *     两个读线程可以共存
 *     读线程与写线程不能共存
 *     写线程与写线程不能共存
 *
 * 锁类型：
 *
 *     独占锁（写锁）：一次只能被一个线程占有
 *     共享锁（读锁）：一个可以被多个线程占有
 */
public class ReadWriteLockDemo2 {

	public static void main(String[] args) throws InterruptedException {

		CountDownLatch latch = new CountDownLatch(6);
		MyMap1 myMap1 = new MyMap1();
		for (int i = 0; i <= 6; i++) {
            final int temp  = i;
            new Thread(()->{
            	myMap1.write(temp, UUID.randomUUID().toString());
            	//减一
				latch.countDown();
			},"线程" +i).start();
		}

		//此行代码以后进入阻塞状态，直到减法计数器减到0
		latch.await();

		for (int i = 1; i <= 6; i++) {
			final int temp = i;
			new Thread(()->{
				myMap1.read(temp);
			},"线程" + (i + 6)).start();
		}
	}
}

class MyMap1{
	volatile Map<Integer,String> map = new HashMap<>();
	//创建读写对象
	ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public Map<Integer,String> getMap(){
		return map;
	}

	void read(Integer key){
		//给读锁加锁
		readWriteLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "读取的key为：" + key);
			String result = map.get(key);
			System.out.println(Thread.currentThread().getName() + "读取key的结果为：" + 	             result);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			//给读锁解锁
			readWriteLock.readLock().unlock();
		}
	}

	void write(Integer key,String value){
		//给写锁加锁
		readWriteLock.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + "执行写操作，key为：" + 				key + "，value为：" + value);
			map.put(key,value);
			System.out.println(Thread.currentThread().getName() + "执行写操作完毕");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//给写锁解锁
			readWriteLock.writeLock().unlock();
		}
	}
}
