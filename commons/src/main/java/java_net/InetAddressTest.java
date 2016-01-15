package java_net;

import java.net.InetAddress;

public class InetAddressTest {
	public static void main(String[] args) throws Exception {
		InetAddress ip = InetAddress.getByName("www.bjtu.edu.cn");
		System.out.println(ip.isReachable(1000));
		System.out.println(ip.getHostAddress());
		InetAddress local = InetAddress.getByAddress(new byte[]{127,0,0,1});
		System.out.println(local.isReachable(2000));
		System.out.println(local.getCanonicalHostName());
		
		byte[] address203 = new byte[]{(byte)211,71,75,(byte)161};
		InetAddress net203 = InetAddress.getByAddress(address203);
		System.out.println(net203.isReachable(2000));
		System.out.println(net203.getCanonicalHostName());
	}
}
