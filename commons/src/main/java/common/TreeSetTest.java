package common;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class TreeSetTest {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String [] args){
		TreeSet set = new TreeSet(
			new Comparator(){
				int r = 0;
				@Override
				public int compare(Object a, Object b) {
					int n1 = Integer.parseInt(a.toString());
					int n2 = Integer.parseInt(b.toString());
					if(n1%2 != n2%2){
						r = (n2%2 - n1%2);
					} else if(n1%2 == 1){
						if(n1 > n2) 
							r = 1;
						else if(n1 < n2) 
							r = -1;
					} else if(n1%2 == 0){
						if(n1 > n2) 
							r = -1;
						else if(n1 < n2)
							r = 1;
					}
					return r;
				}
			}
		);
		Collections.addAll(set, "2 1 3 4 5 6 7 8 9 10".split(" "));
		System.out.println(set);
	}
}
