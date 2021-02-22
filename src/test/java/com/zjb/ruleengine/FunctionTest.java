package com.zjb.ruleengine;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.function.GetPropertyFunction;
import com.zjb.ruleengine.core.value.Variable;
import com.zjb.ruleengine.core.value.VariableFunction;
import org.junit.Test;

/**
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2020-12-07
 */
public class FunctionTest extends BaseTest {

    private String propertyTest="123";

    @Test
    public void testGetPropertyFunction() throws Exception {
        final GetPropertyFunction.GetPropertyFunctionParameter getPropertyFunction = new GetPropertyFunction.GetPropertyFunctionParameter(new FunctionTest(), "propertyTest");
        final VariableFunction variableFunction = new VariableFunction("GetPropertyFunction", getPropertyFunction);

        Variable variable = new Variable(variableFunction, registerFunction());
        final Object value = variable.getValue(new BaseContextImpl());
        System.out.println(variable.getValue(new BaseContextImpl()));
        System.out.println(variable.getValueClass());

    }

    public static void main(String[] args) throws Exception{

    }
}
