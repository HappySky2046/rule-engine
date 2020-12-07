//package com.zjb.ruleengine.core.value;
//
//import com.zjb.ruleengine.core.Context;
//import com.zjb.ruleengine.core.config.Execute;
//import com.zjb.ruleengine.core.enums.DataType;
//import org.apache.commons.lang3.Validate;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.lang.reflect.Field;
//import java.util.Map;
//import java.util.Objects;
//
//
///**
// * 原始变量，execute的返回值是intandceof {@link Number} 或者 {@link String}
// *
// * @author zjb
// * @Date 07/11/2019
// **/
//public class ReferenceVariable extends Variable {
//
//    private static final Logger log = LogManager.getLogger();
//
//
//    /**
//     * 如果返回值是对象，可以取对象中的某个属性性，executeResultAttrCode是属性值的code
//     */
//    private String executeResultAttrCode;
//
//
//    public ReferenceVariable(Map<String, Value> parameters, Execute execute, DataType dataType,String executeResultAttrCode) {
//        super(parameters, execute, dataType);
//        Validate.notBlank(executeResultAttrCode);
//        this.executeResultAttrCode = executeResultAttrCode;
//    }
//
//
//    @Override
//    public Object getValue(Context context) {
//        Object value = super.getValue(context);
//        Field field = null;
//        try {
//            field = value.getClass().getField(executeResultAttrCode);
//            return field.get(value);
//        } catch (NoSuchFieldException e) {
//            try {
//                field = value.getClass().getDeclaredField(executeResultAttrCode);
//                field.setAccessible(true);
//                return field.get(value);
//            } catch (NoSuchFieldException ex) {
//                log.error("变量id:{} 返回值对象没有{}属性", getId(), executeResultAttrCode);
//            } catch (Exception e1) {
//
//            }
//        } catch (Exception e) {
//        }
//       return null;
//    }
//
//
//    @Override
//    public boolean equals(Object other) {
//        if (this == other) {
//            return true;
//        }
//        if (!(other instanceof ReferenceVariable)) {
//            return false;
//        }
//        ReferenceVariable variable = (ReferenceVariable) other;
//        //比较execute
//        if (!Objects.equals(this.getExecute(),variable.getExecute())) {
//            return false;
//        }
//        if (this.getParameterMap().size() != variable.getParameterMap().size()) {
//            return false;
//        }
//        return Objects.equals(this.getParameterMap(), variable.getParameterMap());
//    }
//
//
//
//}
