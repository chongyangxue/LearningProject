package com.learning.algorithm.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xuechongyang
 */
public class LongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0, j = 0; i < s.length() && j < s.length(); ) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                if (set.size() > max) {
                    max = set.size();
                }
                System.out.println(set);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        int max = 0;
        //map存的是重复的字符之前的位置
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < s.length(); j++) {
            if (map.containsKey(s.charAt(j))) {
                int nextIndex = map.get(s.charAt(j)) + 1;
                i = Math.max(nextIndex, i);
            }
            max = Math.max(max, j - i + 1);
            map.put(s.charAt(j), j);
            System.out.println("i=" + i + ", j=" + j + ", max=" + max);
            System.out.println(map);
        }
        return max;
    }

    @Test
    public void test() {
        String str = "abccde";
        System.out.println(lengthOfLongestSubstring2(str));
    }
}
