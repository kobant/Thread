package Juc;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/30 14:41
 */

/**
 * CAS 算法是硬件对于并发操作的支持，CAS 包含了三个操作数：①内存值  V；②预估值
 * A；③更新值  B，当且仅当 V == A 时， V = B; 否则，不会执行任何操作。CAS 保证了原子性，因为失败后并不会阻塞，
 * 效率比一般的同步锁效率高，只是自己写的东西稍微多点儿。
 */
public class TestCompareAndSwap {

	public static void main(String[] args) {

		final CompareAndSwap cas = new CompareAndSwap();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int expectedValue = cas.get();
					boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 101));
					System.out.println(b);
				}
			}).start();
		}
	}
}

class CompareAndSwap{
	private int value;
	//获取内存值
	public synchronized int get(){
		return value;
	}

	//比较
	public synchronized int compareAndSwap(int expectedValue, int newValue){
		int oldValue = value;
		if (oldValue==expectedValue){
			this.value = newValue;
		}
		return oldValue;
	}

	//设置
	public synchronized boolean compareAndSet(int expectedValue, int newValue){
		return expectedValue == compareAndSwap(expectedValue, newValue);
	}
}
