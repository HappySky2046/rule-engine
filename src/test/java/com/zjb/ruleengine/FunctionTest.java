package com.zjb.ruleengine;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.function.Function;
import com.zjb.ruleengine.core.value.Variable;
import com.zjb.ruleengine.core.value.VariableFunction;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-12-07 14:28:44
 */
public class FunctionTest extends BaseTest {

    private String propertyTest = "123";

    @Test
    public void testGetPropertyFunction() throws Exception {
        String funName = "GetJsonPropertyFunction";
        Map<String, Object> object = new HashMap();
        object.put("name", "张三");
        object.put("age", 4);


        final FunctionHolder holder = registerFunction();
        final Function function = holder.getFunction(funName);
        final List<Function.Parameter> paramters = function.getParamters();
        final HashMap<String, Object> objectObjectHashMap = new HashMap<>();



        final VariableFunction variableFunction = new VariableFunction(funName, new HashMap<>(),registerFunction());

        Variable variable = new Variable(DataTypeEnum.POJO,variableFunction);
        final Object value = variable.getValue(new BaseContextImpl());
        System.out.println(variable.getValue(new BaseContextImpl()));
        System.out.println(variable.getDataTypeEnum());

    }

    public static void main(String[] args) throws Exception {

    }
}
