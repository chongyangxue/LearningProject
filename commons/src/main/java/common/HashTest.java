package common;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class HashTest {
	private int areaCode;
	private int phoneCode;
	
	public HashTest(int areaCode, int phoneCode) {
		this.areaCode = areaCode;
		this.phoneCode = phoneCode;
	}

	@Override public boolean equals(Object o) {
		if(o == this)
			return true;
		if(!(o instanceof HashTest))
			return false;
		HashTest ht = (HashTest)o;
		return ht.areaCode == this.areaCode &&
				ht.phoneCode == this.phoneCode;
	}

/*
	@Override
	public int hashCode() {
		int result = 17;
		result = this.areaCode + 31 * result;
		result = this.phoneCode + 31 * result;
		return result;
	}*/

	public static void main(String[] args) {
		HashMap<HashTest, String> map = new HashMap<HashTest, String>();
		HashTest test1 = new HashTest(112, 334);
		HashTest test2 = new HashTest(112, 334);
		System.out.println("test1 equals test2 ? " + test1.equals(test2));
		map.put(test1, "first");
		map.put(test2, "second");
		for(Entry<HashTest, String> entry : map.entrySet()){
			System.out.println(entry.getKey().hashCode() + " -- " + entry.getValue());
		}
		
		Map<HashTest, String> map1 = new LinkedHashMap<HashTest, String>();
	}
}
