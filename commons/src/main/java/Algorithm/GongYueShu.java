package Algorithm;

/**
 * 求两个数的最大公约数
 * @author Sachiel
 *
 */
public class GongYueShu {

	public static int getGongyueshu(int a, int b){
		if(a == b)
			return 1;
		else{
			if(a == 0)
				return b;
			else if(b == 0)
				return a;
		}
		return a > b ? getGongyueshu(b, a % b) : getGongyueshu(a, b % a);
	}
	
	public static void main(String[] args) {
		System.out.println(getGongyueshu(30, 42));
	}

}
