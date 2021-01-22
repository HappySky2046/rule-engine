package com.zjb.ruleengine.value;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.value.Constant;
import com.zjb.ruleengine.core.value.Element;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 赵静波
 * Created on 2021-01-22
 */
public class ConstantTest {

    public static final String ELEMENT = "element";

    @Test
    public void element() throws Exception{
        final JsonNode jsonNode = new ObjectMapper().readTree("{\"id\":1}");
        final Constant element = new Constant(DataTypeEnum.JSONOBJECT, jsonNode);
        BaseContextImpl context = new BaseContextImpl();
        final Object value = element.getValue(context);
        Assert.assertTrue(value.getClass()==jsonNode.getClass());
    }
}
