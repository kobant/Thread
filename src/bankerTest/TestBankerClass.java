package bankerTest;

import java.util.Scanner;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/29 15:22
 */
public class TestBankerClass {
	public static void main(String[] args) {
		boolean Choose = true;
		String C;
		Scanner in = new Scanner(System.in);
		BankerClass T = new BankerClass();
		System.out.println("这是一个三个进程，初始系统可用三类资源为{10,8,7}的银行家算法：");

		T.setSystemVariable();
		while (Choose == true) {
			T.setRequest();
			System.out.println("您是否还要进行请求：y/n?");
			C = in.nextLine();
			if (C.endsWith("n")) {
				Choose = false;
			}
		}
	}
}
