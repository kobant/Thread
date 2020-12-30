package com.company;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/27 14:32
 */
public class StaticPoxy {

	public static void main(String[] args) {
		WinddyConpany w  = new WinddyConpany(new You());
		w.HappyMarry();
	}

}


interface Marry{
	void HappyMarry();
}

class You implements Marry{

	@Override
	public void HappyMarry() {
		System.out.println("要结婚很开兴。。。。。。。。");
	}
}


class WinddyConpany implements Marry{

	private Marry target;

	public WinddyConpany(Marry target) {
		this.target = target;
	}

	@Override
	public void HappyMarry() {

		before();

		this.target.HappyMarry();

		after();
	}

	private void after() {
		System.out.println("结婚之后，哈哈哈");
	}

	private void before() {
		System.out.println("结婚之前。。。");
	}
}