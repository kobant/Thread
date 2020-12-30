package syn;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/27 17:02
 */
public class SynBuyTicker {


	public static void main(String[] args) {
		BuyTickers buyTickers = new BuyTickers();

		new Thread(buyTickers,"A").start();
		new Thread(buyTickers,"B").start();
		new Thread(buyTickers,"C").start();
	}

}


class BuyTickers implements Runnable {

	//票
	private int ticketMuins = 10;

	boolean flag = true;

   private final ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {

		while (flag){
			try {
				lock.lock();
				buy();
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				lock.unlock();
			}

		}

	}

	private void buy(){
		if (ticketMuins<=0){
			flag = false;
			return;
		}
		//

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "拿到"+ ticketMuins--);
	}

}