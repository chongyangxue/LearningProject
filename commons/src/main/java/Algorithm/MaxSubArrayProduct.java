package Algorithm;

/**
 * 求数组的连续子数组的积的最大值
 * @author Sachiel
 *
 */
public class MaxSubArrayProduct {

	public static float MaxProduct(float[] array){
		float max = 1;
		float maxCurrent = 1;
		float minCurrent = 1;
		
		for(int i = 0; i < array.length; i++){
			maxCurrent *= array[i];
			minCurrent *= array[i];
			float tempMaxCurrent = maxCurrent;
			maxCurrent = max(array[i], tempMaxCurrent, minCurrent);
			minCurrent = min(array[i], tempMaxCurrent, minCurrent);
			
			if(maxCurrent < minCurrent){
				float temp = minCurrent;
				minCurrent = maxCurrent;
				maxCurrent = temp;
			}
			if(maxCurrent > max)
				max = maxCurrent;
			System.out.println("maxCurrent:" + maxCurrent + " minCurrent:" + minCurrent);
			System.out.println("	max:" + max );
		}
		return max;
	}
	
	public static float max(float x, float y, float z){
		if(x < y)
			x = y;
		if(x < z)
			x = z;
		return x;
	}
	
	public static float min(float x, float y, float z){
		if(x > y)
			x = y;
		if(x > z)
			x = z;
		return x;
	}
	
	public static void main(String[] args){
		float[] array = new float[]{2, -2, 3, 0.1f, -4, 5, -3, 1, 2};
		System.out.println("MaxProduct: " +MaxProduct(array));
	}
}
