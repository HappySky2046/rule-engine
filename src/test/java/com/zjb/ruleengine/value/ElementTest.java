package com.zjb.ruleengine.value;

import com.zjb.ruleengine.core.BaseContextImpl;
import com.zjb.ruleengine.core.enums.DataTypeEnum;
import com.zjb.ruleengine.core.value.Element;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 赵静波
 * Created on 2021-01-22
 */
public class ElementTest {

    public static final String ELEMENT = "element";

    @Test
    public void element() {
        final Element element = new Element(DataTypeEnum.BOOLEAN, ELEMENT);
        BaseContextImpl context = new BaseContextImpl();
        context.put(ELEMENT, true);
        final Object value = element.getValue(context);
        Assert.assertEquals(value, true);
    }
}
