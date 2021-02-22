package com.zjb.ruleengine.core;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 46133
 */
public interface Context extends Serializable {

    ///**
    // * 预留字段，规则不能使用该预留字段
    // */
    //enum ReserveWord{
    //    RESERVE_WORD_APPID,RESERVE_WORD_SIGN,RESERVE_WORD_BIZPARAMS,RESERVE_WORD_TIMESTAMP;
    //}
    /**
     * 添加参数信息到上下文中
     *
     * @param name   参数名称
     * @param object 参数值
     */
    <T> T put(String name, T object);

    /**
     * 把环境中某键值的名字换成另外的名字
     *
     * @param key    旧的参数名称
     * @param newKey 新的参数名称
     * @return
     */
    boolean renameKey(String key, String newKey);

    /**
     * 移除参数名称对应的参数值
     *
     * @param name 参数名称
     * @return 返回参数名称对应的参数值
     */
    <T> T remove(String name);

    /**
     * 获取对象
     *
     * @param name 参数名称
     * @return 获取参数名称对应的参数值
     */
    <T> T get(String name);

    /**
     * 存储参数值
     *
     * @param map 多个参数组成的map数据结构
     */
    void putAll(Map<String, Object> map);

    /**
     * 获取参数名称对应的参数值，如果参数值不存在，那么返回默认值
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 返回参数值
     */
    <T> T get(String name, T defaultValue);

    /**
     * 返回上下文中的参数数量
     *
     * @return
     */
    int size();

    /**
     * 检测参数是否存在
     *
     * @param name 参数名称
     * @return 返回是否存在
     */
    boolean exist(String name);

    /**
     * 上下文是否存在参数名称对应的参数信息，
     *
     * @param name 参数名称
     * @return 存在返回this, 不存在返回null
     */
    boolean contain(String name);

    /**
     * 删除上下文中的所有参数信息
     */
    void clear();

    /**
     * 返回上下文中所有参数
     *
     * @return
     */
    Map<String, Object> getItemMap();
}
