package com.zjb.ruleengine;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.Context;
import com.zjb.ruleengine.core.config.FunctionHolder;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.function.Function;
import com.zjb.ruleengine.core.value.*;

import java.util.Collection;

/**
 * @author 赵静波 <wb_zhaojingbo@kuaishou.com>
 * Created on 2020-12-04
 */
public class VariableTest {
    public static void main(String[] args) {
        final FunctionHolder functionHolder = new FunctionHolder();
        functionHolder.registerFunction(new Func());
        functionHolder.registerFunction(new Func1());

        final Param param0 = new Param();
        final Variable variable0 = new Variable(new VariableFunction<String>("Func1", "zhaojingbo11"), functionHolder);
        param0.setVariable(variable0);
        param0.setEle(new Element(DataTypeEnum.BOOLEAN, "ele1"));

        final Param param = new Param();
        final Variable variable1 = new Variable(new VariableFunction<Param>("Func", param0), functionHolder);
        param.setVariable(variable1);
        param.setEle(new Element(DataTypeEnum.BOOLEAN, "ele"));
        final Variable variable = new Variable(new VariableFunction<Param>("Func", param), functionHolder);


        System.out.println(variable.getValue(new BaseContextImpl()));
        final Collection<Element> elements = variable.collectParameter();
        System.out.println(variable.collectParameter());
        System.out.println();
    }

    static class Func extends Function<Param, String> {
        @Override
        public String execute(Context context, Param param) {
            return param.variable.getValue(context) + "";
        }
    }

    static class Func1 extends Function<String, String> {
        @Override
        public String execute(Context context, String param) {
            return "123" + param;
        }
    }

    static class Param {
        private Value variable;
        private Value ele;

        public Value getVariable() {
            return variable;
        }

        public Value getEle() {
            return ele;
        }

        public void setEle(Value ele) {
            this.ele = ele;
        }

        public void setVariable(Value variable) {
            this.variable = variable;
        }
    }
}
