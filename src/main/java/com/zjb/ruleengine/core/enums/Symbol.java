
package com.zjb.ruleengine.core.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵静波
 * @date 2020-09-15 11:30:30
 */
public enum Symbol implements Serializable {
    number_eq(DataTypeEnum.NUMBER, "=", "等于"),
    number_gt(DataTypeEnum.NUMBER, ">", "大于"),
    number_ge(DataTypeEnum.NUMBER, ">=", "大于等于"),
    number_lt(DataTypeEnum.NUMBER, "<", "小于"),
    number_le(DataTypeEnum.NUMBER, "<=", "小于等于"),
    number_ne(DataTypeEnum.NUMBER, "!=", "不等于"),
    number_in(DataTypeEnum.NUMBER, "in", "在..之内"),
    not_in(DataTypeEnum.NUMBER, "notIn", "不在..之内"),

    str_eq(DataTypeEnum.STRING, "=", "等于"),
    str_ne(DataTypeEnum.STRING, "!=", "不等于"),
    str_start_with(DataTypeEnum.STRING, "startWith", "以什么开始"),
    str_end_with(DataTypeEnum.STRING, "endWith", "以什么结束"),
    str_not_start_with(DataTypeEnum.STRING, "notStartWith", "不以什么开始"),
    str_not_end_with(DataTypeEnum.STRING, "notEndWith", "不以什么结束"),
    str_index_of(DataTypeEnum.STRING, "indexOf", "字符串包含"),
    str_not_index_of(DataTypeEnum.STRING, "notIndexOf", "字符串不包含"),
    str_in(DataTypeEnum.STRING, "in", "在..之内"),
    str_not_in(DataTypeEnum.STRING, "notIn", "不在..之内"),

    collection_contain(DataTypeEnum.COLLECTION, "contain", "包含"),
    collection_not_contain(DataTypeEnum.COLLECTION, "notContain", "不包含"),
    set_eq(DataTypeEnum.COLLECTION, "=", "相等"),
    collection_in(DataTypeEnum.COLLECTION, "in", "在..之内"),
    collection_not_in(DataTypeEnum.COLLECTION, "notIn", "不在..之内"),

    boolean_in(DataTypeEnum.BOOLEAN, "in", "在..之内"),
    boolean_not_in(DataTypeEnum.BOOLEAN, "notIn", "不在..之内"),
    boolean_eq(DataTypeEnum.BOOLEAN, "=", "等于"),
    boolean_ne(DataTypeEnum.BOOLEAN, "!=", "不等于");

    private DataTypeEnum type;
    private String symbol;
    private String name;

    private static Map<DataTypeEnum, List<Symbol>> map = new HashMap<>();
    private static Map<DataTypeEnum, Map<String, Symbol>> symbolMap = new HashMap<>();
    private static Map<String, Symbol> symbolNameMap = new HashMap<>();

    static {
        Symbol[] constants = Symbol.class.getEnumConstants();
        for (Symbol constant : constants) {
            List<Symbol> symbols = map.getOrDefault(constant.type, new ArrayList<>());
            symbols.add(constant);
            if (!map.containsKey(constant.type)) {
                map.put(constant.type, symbols);
            }
            Map<String, Symbol> symbolMap1 = symbolMap.getOrDefault(constant.getType(), new HashMap<>());

            if (!symbolMap1.containsKey(constant.symbol)) {
                symbolMap1.put(constant.symbol, constant);
            }
            if (!symbolMap.containsKey(constant.getType())) {
                symbolMap.put(constant.getType(), symbolMap1);
            }
            symbolNameMap.put(constant.name(), constant);
        }
    }

    public DataTypeEnum getType() {
        return type;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    Symbol(DataTypeEnum type, String symbol, String name) {
        this.type = type;
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * s
     * 根据 type 获取 Symbols
     *
     * @param type
     * @return
     */
    public static List<Symbol> listSymbolsByType(DataTypeEnum type) {
        return map.get(type);
    }

    public static Symbol getSymbolByDataType(DataTypeEnum type, String symbol) {
        return symbolMap.get(type).get(symbol);
    }

    public static Symbol getSymbolByEnumName(String name) {
        return symbolNameMap.get(name);
    }
}