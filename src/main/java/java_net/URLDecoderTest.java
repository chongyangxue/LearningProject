package java_net;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLDecoderTest {
	public static void main(String[] args) throws Exception {
		String urlStr = URLEncoder.encode("申请增加App Quota", "GBK");
		System.out.println(urlStr);
		
		String keyWord = URLDecoder.decode("http%3a%2f%2fwww%2ebaidu%2ecom", "UTF-8");
		System.out.println(keyWord);
	}
}
