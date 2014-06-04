package memcache;

import java.util.ArrayList;
import java.util.List;

import com.sohu.sce.repackaged.net.rubyeye.xmemcached.MemcachedClient;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.MemcachedClientBuilder;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.XMemcachedClientBuilder;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.command.BinaryCommandFactory;
import com.sohu.sce.repackaged.net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;

public class MemcacheTest {
	public static void main(String args[]) {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder("1025", 
				"9f39c9472045f05018f1d32277508675");//生成的uid以及password

		builder.setSessionLocator(new KetamaMemcachedSessionLocator());//一致性hash的设置

		builder.setCommandFactory(new BinaryCommandFactory());//必须得用二进制协议

		try{
			MemcachedClient mc = builder.build();
			if(mc.get("name") == null){
				List<String> names = new ArrayList<String>();
				names.add("chong");
				names.add("yang");
				names.add("xue");
				names.add("@");
				names.add("sohu");
				mc.set("name", 200, names);
				List<String> results = mc.get("name");
				System.out.println(results);
			}else{
				System.out.println("no match result");
			}
			mc.shutdown();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
