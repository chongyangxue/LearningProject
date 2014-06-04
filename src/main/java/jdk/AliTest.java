package jdk;


public class AliTest {
	public static void move(int n, int s[]) {
		for(int i = 1; i < n; i++) {
			if(s[i] > 10){
				if(s[i] > s[i-1]) {
					int tmp = s[i] - 10;
					s[i - 1] = s[i-1] + tmp;
					s[i] = 10;
				}else {
					int tmp = s[i-1] - 10;
					s[i] = s[i] + tmp;
					s[i-1] = 10;
				}
				
				if(s[i] > s[i+1]) {
					int tmp = s[i] - 10;
					s[i - 1] = s[i-1] + tmp;
					s[i] = 10;
				}else {
					int tmp = s[i+1] - 10;
					s[i] = s[i] + tmp;
					s[i+1] = 10;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int s[] = {7, 11, 9, 12, 13, 8, 7, 15, 8};
		boolean success = false;
		while(!success) {
			for(int i : s) {
				if(i > 10) {
					move(9, s);
					success = false;
					break;
				}
				success = true;
			}
		}
		
		System.out.println(s);
	}
}
