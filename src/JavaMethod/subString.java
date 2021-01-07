package JavaMethod;

import com.sun.deploy.util.StringUtils;
import org.junit.Test;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2021/1/6 17:54
 */
public class subString {

	@Test
	public void test1(){
		String sb = "bbbdsajjds";
		System.out.println(sb.substring(2));//bdsajjds

		System.out.println("------------------------------------");
		System.out.println(sb.substring(2,4));//bd

		System.out.println("-----------------------------------------");
		//3.通过StringUtils提供的方法
		//System.out.println(StringUtils.substringBefore(“dskeabcee”, “e”););
	}
}
