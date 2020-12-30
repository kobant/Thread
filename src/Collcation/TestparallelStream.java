package Collcation;

import javafx.scene.chart.PieChart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2020/12/29 18:18
 */
public class TestparallelStream {

	@Test
	public void test01() throws InterruptedException {
		List<Apple> appleList = new ArrayList<>();
		appleList.add(new Apple(11));
		appleList.add(new Apple(12));
		appleList.add(new Apple(13));
		appleList.add(new Apple(14));
		Date begin = new Date();

		for (Apple apple:appleList){
			apple.setPrice(5.0*apple.getWeight());
			Thread.sleep(1000);
		}
		Date end = new Date();
		System.out.println("苹果数量：{}个 :"+appleList.size() +" :耗时："+(end.getTime() - begin.getTime())/1000+" s");
	}

	@Test
	public void test02(){
		List<Apple> appleList = new ArrayList<>();
		appleList.add(new Apple(11));
		appleList.add(new Apple(12));
		appleList.add(new Apple(13));
		appleList.add(new Apple(14));
		Date begin = new Date();
		appleList.parallelStream().forEach(apple -> {
			apple.setPrice(5.0 * apple.getWeight() / 1000);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Date end = new Date();
		System.out.println("苹果数量：{}个 :"+appleList.size() +" :耗时："+(end.getTime() - begin.getTime())/1000+" s");
	}
}
