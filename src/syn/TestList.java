package syn;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/27 17:19
 */
public class TestList {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();

		for (int i = 0; i < 1000; i++) {

			new Thread(()->{
				synchronized (list){
					list.add(Thread.currentThread().getName());
				}
			}).start();
		}
		try {
			Thread.sleep(3000);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		System.out.println(list.size());
	}
}
