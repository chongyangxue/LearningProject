package lambba;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xuechongyang on 16/7/21.
 */
public class LambdaTest {

    @Test
    public void testLambda() {
        System.out.println(testTypeInfer(() -> {
            return "hello";
        }));

        ExecutorService thread = Executors.newFixedThreadPool(1);

        thread.submit(() -> {
            System.out.println("lambda");
        });
    }

    private String testTypeInfer(TestInterface testInterface) {
        return testInterface.test1();
    }

    @Test
    public void test2() {
        TestInterface2 interface2 = (x) -> {return  x + "!!";};
        System.out.println(interface2.test2(2));
    }

}
