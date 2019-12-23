package com.learning.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Aviator是一个高性能、轻量级的 java 语言实现的表达式求值引擎, 主要用于各种表达式的动态求值。
 * https://github.com/killme2008/aviator/wiki
 *
 * @author xuechongyang
 */
public class TestAviator {

    @Test
    public void test1() {
        long result = (long) AviatorEvaluator.execute("1+2+3");
        Assert.assertEquals(result, 6L);
    }

    /**
     * 执行多条语句
     */
    @Test
    public void test2() {
        long result = (long) AviatorEvaluator.execute("print('hello world'); 1+2+3 ; 100-1");
        Assert.assertEquals(result, 99L);
    }

    /**
     * 变量传值
     */
    @Test
    public void test3() {
        String yourName = "Michael";
        Map<String, Object> env = new HashMap<>();
        env.put("yourName", yourName);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        System.out.println(result);  // hello Michael

        result = (String) AviatorEvaluator.exec(" 'hello ' + yourName ", yourName);
        System.out.println(result);  // hello Michael
    }

    /**
     * 调用函数
     */
    @Test
    public void test4() {
        System.out.println(AviatorEvaluator.execute("string.substring('hello', 1, 2)"));
        boolean result = (boolean)AviatorEvaluator.execute("string.contains(\"test\", string.substring('hello', 1, 2))");
        Assert.assertEquals(result, true);
    }
}
