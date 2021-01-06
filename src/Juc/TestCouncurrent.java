package Juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2021/1/4 10:44
 */
public class TestCouncurrent {

	public static void main(String[] args) {
		ThreadDemo02 threadDemo02 = new ThreadDemo02();
		for (int i = 0; i < 10; i++) {
			new Thread(threadDemo02).start();
		}
	}

}
//创建10个线程同时访问
class ThreadDemo02 implements Runnable{

	//private static List<String> list = Collections.synchronizedList(new ArrayList<>());
	private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

	static {
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
	}
	@Override
	public void run() {
		list.forEach(item->{
			System.out.println(Thread.currentThread().getName()+":线程名"+item);
			list.add("ddd");
		});
	}
}