package jdk;

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
				String result = matcher.group();
				System.out.println(result);
			}
	}
}
