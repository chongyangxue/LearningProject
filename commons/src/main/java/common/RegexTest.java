package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
	@Test
	public void testDomain(){
		Pattern reg = Pattern.compile("^(([0-9a-z]+[0-9a-z\\-]*[0-9a-z])|[0-9a-z])+(\\.[0-9a-z]+[0-9a-z\\-]*[0-9a-z]+)+$");
		//Pattern reg = Pattern.compile("^(([0-9a-z]+[0-9a-z\\-]*[0-9a-z])|[0-9a-z])+(.*)");
		Matcher matcher = reg.matcher("t.sohu.com");
			if(matcher.find()) {
				String result = matcher.group(2);
				System.out.println(result);
			}
	}
	
	public static boolean isDomain(String domain) {
        String str = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(domain);
        return m.matches();
    }

    public static boolean isIP(String ip) {
        String str = "^\\d+\\.\\d+\\.\\d+\\.\\d+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    public static boolean isMail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
