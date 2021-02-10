package com.zjb.ruleengine.value;

import cn.hutool.core.lang.Func;
import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.function.Function;
import com.zjb.ruleengine.core.value.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 赵静波
 * @date 2020-12-04 17:25:54
 */
public class VariableTest {
    public static void main(String[] args) {
        final FunctionHolder functionHolder = new FunctionHolder();
        final Func func = new Func();
        functionHolder.registerFunction(func);
        //该函数的参数
        final List<Function.Parameter> paramters = func.getParamters();
        //模拟变量的参数，假如参数全部需要element(即用户输入)
        final Map<String, Element> varFunciton = paramters.stream().collect(Collectors.toMap(Function.Parameter::getName, para -> new Element(para.getDataTypeEnum(), para.getName())));

        final Variable variable = new Variable(DataTypeEnum.STRING,new VariableFunction(func.getName(), varFunciton, functionHolder));
        final Collection<Element> elements = variable.collectParameter();
        System.out.println("变量需要的参数：" + elements);
        //用户传入的参数
        final BaseContextImpl context = new BaseContextImpl();
        context.put("param", "input");
        System.out.println(variable.getValue(context));

    }

    /**
     * function, 返回 “123”+参数
     */
    public static class Func extends Function<String, String> {
        @Override
        public String execute(String param) {
            return "123" + param;
        }
    }


}
