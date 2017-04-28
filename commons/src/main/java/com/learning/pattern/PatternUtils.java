package com.learning.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuechongyang on 17/4/28.
 */
public class PatternUtils {

    public static void main(String[] args) {
        String poiID = "123456%3F123";
        String poiID2 = "123456?service=test&test=123";
        Pattern pattern = Pattern.compile("[^0-9].+");
        Matcher matcher = pattern.matcher(poiID2);
        String result = matcher.replaceAll("");
        System.out.println(result);


    }

}
