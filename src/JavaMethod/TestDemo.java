package JavaMethod;

import static java.lang.String.valueOf;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;
import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/**
 * @Description:
 * @Author: liaocongcong
 * @Date: 2021/1/6 18:13
 */
public class TestDemo {
	public static void main(String[] args) {
		char ch ;
		System.out.println("charAt："+"abc".charAt(1));

		String h = "聪聪";
		String s="this is a demo of the getChars method.";
		char buf[] = new char[20];
		s.getChars(10,14,buf,0);

		System.out.println("------------------------------");
		char[] tsr= s.toCharArray();
		System.out.println(tsr);
		byte[] bytes= s.getBytes();
		System.out.println(bytes.toString());

		System.out.println("--------------indexOf()和lastIndexOf()---------------");
		int i = s.indexOf("t");
		System.out.println(i);
		String s1 = s.replaceFirst("t", "o");
		System.out.println(s1);
		int d = s.lastIndexOf("d");
		System.out.println(d);

		System.out.println("--------------------substring()--------------------");
		String substring = s.substring(1);
		System.out.println(substring);
		String substring1 = s.substring(0, 4); //不包括最后一位
		System.out.println(substring1);

		System.out.println("-----------------------concat() 连接两个字符串---------------------------");
		System.out.println(s.concat(h));

		System.out.println("-------------------replace() 替换--------------------------");
		String ra = h.replace('聪', '哈' );
		System.out.println(ra);

		int one= 123;
		System.out.println(valueOf(one));
		System.out.println("------------17、toLowerCase() 转换为小写\n" +
				"\n" +
				"18、toUpperCase() 转换为大写-------------");

		String s2 = toLowerCase(s);
		String toUppe=toUpperCase(s);
		System.out.println("小写："+s2);
		System.out.println("大写："+toUppe);

		System.out.println("---------------StringBuffer构造函数----------------");
		/**
		 * 19、StringBuffer构造函数
		 * 　　StringBuffer定义了三个构造函数：
		 * 　　StringBuffer()
		 * 　　StringBuffer(int size)
		 * 　　StringBuffer(String str)
		 * 　　StringBuffer(CharSequence chars)
		 */

		StringBuffer stringBuffer = new StringBuffer(s);



		int capacity = stringBuffer.capacity();
		System.out.println(capacity);

		stringBuffer.setLength(500);
		int length = stringBuffer.length();
		System.out.println(length);
		System.out.println("-------------append() 可把任何类型数据的字符串表示连接到调用的StringBuffer对象的末尾。---------------");
		int a = 42;
		StringBuffer sb = new StringBuffer(40);
		String s3 = sb.append("a=").append(a).append("!").toString();
		System.out.println(s3);

		System.out.println("------insert() 插入字符串-----");

		String kary="this is a demo of the getChars method.";
		StringBuffer st = new StringBuffer(kary);
		StringBuffer cat = st.insert(0, "聪聪");
		System.out.println(cat);
		StringBuffer reverse = stringBuffer.reverse();
		System.out.println(reverse);

	}
}
