package com.learning.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xuechongyang
 */
public class TestAviator {

    @Test
    public void test() {
        long result = (Long) AviatorEvaluator.execute("1+2+3");
        Assert.assertEquals(result, 6L);
    }
}
