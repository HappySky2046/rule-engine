package com.zjb.ruleengine;

import com.zjb.ruleengine.core.function.HttpObjectFunction;
import org.junit.Test;

/**
 * @author 赵静波
 * @date 2020-12-20 18:33:04
 */
public class HttpObjectFunctionTest {
    @Test
    public void test() {
        final String execute = new DemoHttpFunticonTest().execute("123");
        System.out.println(execute);
    }


    public static class DemoHttpFunticonTest extends HttpObjectFunction<String, String> {

        @Override
        protected String getUrl() {
            return "http://localhost:8080/test";
        }
    }
}

