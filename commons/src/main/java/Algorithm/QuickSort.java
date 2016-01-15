package Algorithm;

public class QuickSort {
	public static int[] swap(int[] array, int i, int j){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return array;
	}
	
	public static int[] quickSort(int[] array, int start, int end){

		if(start < end){
			int mid = array[start];
			int i = start;
			int j = end;
			while(true){
			
				while(i < end && array[i] <= mid)
					i++;
				while(j > start && array[j] >= mid)
					j--;
				if(i < j){
					array = swap(array, i, j);
				}else{
					break;
				}
			}
			array = swap(array, start, j);
			
			for(int k : array) {
				if(k == mid)
					System.out.print("[" + k + "] ");
				else
					System.out.print(k + " ");
			}
			System.out.println();
			
			quickSort(array, start, j - 1);
			quickSort(array, j + 1, end);
			return array;
		}
		return null;
	}
	
	
	public static void main(String[] args){
		int[] array = new int[]{1, 3, 7, 4, 9, 2, 6, 8, 5, 0};
		array = quickSort(array, 0, array.length - 1);
		for(int i : array) {
			System.out.print(i + " ");
		}
	}
}
