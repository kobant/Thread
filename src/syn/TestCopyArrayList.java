package syn;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/27 19:31
 */
public class TestCopyArrayList {

	public static void main(String[] args) {

		CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

		for (int i = 0; i < 1000; i++) {
			new Thread(()->{
				copyOnWriteArrayList.add(Thread.currentThread().getName());
			}).start();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(copyOnWriteArrayList.size());
	}

}
