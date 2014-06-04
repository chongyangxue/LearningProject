package Algorithm;

/**
 * 判断是否存在一个区间是另一个区间的子区间
 * @author Sachiel
 *
 */
public class IntervalTest {
	static int interval(int[] a) {
		for(int i = 0; i <= a.length - 2; i=i+2){
			if(a[i] > a[i+1])
				return 0;
		}
		
		for(int i = 0 ; i <= a.length - 2; i=i+2){
			for(int j = i + 2 ; j <= a.length - 2; j=j+2){
				if((a[i] <= a[j]) && (a[i+1] >= a[j+1]))
					return 1;
				else if((a[i] >= a[j]) && (a[i+1] <= a[j+1]))
					return 1;
			}
		}
		return 0;
	}
	
	public static void main(String[] args){
		int[] a = new int[]{3, 4, 7, 8, 2, 3};
		int[] b = new int[]{1, 4, 2, 3};
		System.out.println(interval(a));
	}
}
