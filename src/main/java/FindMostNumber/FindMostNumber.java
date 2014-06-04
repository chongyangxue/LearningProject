package FindMostNumber;

/**
 * 找出一个数组中出现次数超过一半的数
 * @author Sachiel
 *
 */
public class FindMostNumber {
	public static int getMost(int[] array) {
		int ntimes = 0;
		int mostNumber = 0;
		for(int i = 0; i < array.length; i++) {
			if(ntimes == 0) {
				mostNumber = array[i];
				ntimes = 1;
			} else {
				if(array[i] == mostNumber)
					ntimes ++;
				else
					ntimes --;
			}
		}
		return mostNumber;
	}
	
	public static void main(String[] args) {
		int[] array = new int[]{5, 2, 5, 5, 9, 5, 5, 6, 6, 3}; 
		int number = FindMostNumber.getMost(array);
		System.out.println(number);
		
	}
}
