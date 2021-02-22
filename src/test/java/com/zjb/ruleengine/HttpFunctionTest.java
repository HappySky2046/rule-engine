package com.zjb.ruleengine;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.function.HttpFunction;
import org.junit.Test;

/**
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2020-12-20
 */
public class HttpFunctionTest {
    @Test
    public void test() {
        final String execute = new DemoHttpFunticonTest().execute(new BaseContextImpl(), "123");
        System.out.println(execute);
    }


   public static class DemoHttpFunticonTest extends HttpFunction<String, String> {

        @Override
        protected String getUrl() {
            return "http://rap2api.taobao.org/app/mock/data/1829389";
        }
    }
}

