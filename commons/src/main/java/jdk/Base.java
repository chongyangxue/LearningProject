package jdk;

public class Base {
	static void procedure() {
		try {
			int c[] = {1};
			c[10] = 99;
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("数组越界异常" + e);
		}
	}
	
	public static void main(String args[]) {
		try{
			procedure();
			int a = args.length;
			int b = 43/a;
			System.out.println(b);
		}catch(Exception e) {
			System.out.println("除0异常, 此行也可以打印");
		}
	}
}
