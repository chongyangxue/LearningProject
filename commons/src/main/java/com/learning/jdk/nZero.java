package com.learning.jdk;
/**
        结论1：对于n的阶乘n！，其因式分解中，如果存在一个因子“5”，那么它必然对应着n！末尾的一个“0”。
        下面对这个结论进行证明：
   (1)当n < 5时, 结论显然成立。
   (2)当n >= 5时，令n！= [5k * 5(k-1) * ... * 10 * 5] * a，其中 n = 5k + r (0 <= r <= 4)，a是一个不含因子“5”的整数。
        对于序列5k, 5(k-1), ..., 10, 5中每一个数5i(1 <= i <= k)，都含有因子“5”，并且在区间(5(i-1),5i)(1 <= i <= k)内存在偶数，也就是说，a中存在一个因子“2”与5i相对应。即，这里的k个因子“5”与n！末尾的k个“0”一一对应。
       我们进一步把n！表示为：n！= 5^k * k! * a（公式1），其中5^k表示5的k次方。很容易利用(1)和迭代法，得出结论1。

      上面证明了n的阶乘n！末尾的“0”与n！的因式分解中的因子“5”是一一对应的。也就是说，计算n的阶乘n！末尾的“0”的个数，可以转换为计算其因式分解中“5”的个数。

      令f(x)表示正整数x末尾所含有的“0”的个数, g(x)表示正整数x的因式分解中因子“5”的个数，则利用上面的的结论1和公式1有：
   f(n!) = g(n!) = g(5^k * k! * a) = k + g(k!) = k + f(k!)
      所以，最终的计算公式为：
      当0 < n < 5时，f(n!) = 0; 
      当n >= 5时，f(n!) = k + f(k!), 其中 k = n / 5（取整）。
 *
 */
public class nZero {
	public static int f(int n) {
		if(n < 5)
			return 0;
		int k = n/5;
		if(k >= 5)
			return k+ f(k/5);
		else
			return k;
	}
	
	public static void main(String[] args) {
		System.out.println("尾部0的个数：" + nZero.f(100));
		nZero.count(100);
	}
	
	public static void count(int n) {
		double count = 1;
		for(int i = n; i > 1; i--){
			count = count * i;
		}
		System.out.println(count);
			
	}
}
