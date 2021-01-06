package JavaMethod;

import org.junit.Test;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2021/1/6 14:19
 */
public class Split {

	@Test
	public void test1(){
		String str = "boo:and:foo";
		String[] result1=  str.split(":");

		for (String s : result1) {
			System.out.println(s);
		}

		/**
		 * boo
		 * and
		 * foo
		 */

		System.out.println("--------------第二轮-----------");

		String [] result2 = str.split("o");
		for (String s : result2) {
			System.out.println(s);
		}

		/**
		 * b
		 *
		 * :and:f
		 */
	}

	@Test
	public void test2(){
		String str = "boo.and.foo";
     /*
     1.because the symbol dot is a special symbol in regular expression,so you couldn't get any result
     因为符号点是正则表达式中的特殊符号，所以您无法获得任何结果
     2.symbol "." not be used as a common symbol but a regular expression?
     符号“.”不是用作通用符号而是正则表达式？
      */
		String [] result = str.split("a");
		for(String s: result) {
			System.out.println(s);
		}
	}

}
