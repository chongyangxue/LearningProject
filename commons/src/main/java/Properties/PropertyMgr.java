package Properties;

import java.util.Properties;

public class PropertyMgr {
	private static Properties props = new Properties();

	static {
		try {
			props.load(PropertyMgr.class
					.getResourceAsStream("config.properties"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(PropertyMgr.getProperty("name"));
	}
}
