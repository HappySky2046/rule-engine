package com.zjb.ruleengine.core.rule;

public abstract class RuleSetParser {

    //private static final Logger log = LogManager.getLogger();
    ///**
    // * ----------------------------------RuleSet-------------------------------
    // */
    //protected static final String RULE_CODE = getKey(RuleSet::getCode);
    //protected static final String NAME = getKey(RuleSet::getName);
    //protected static final String DESCRIPTION = getKey(RuleSet::getDescription);
    //protected static final String HIT_POLICY = getKey(NormalRuleSet::getHitPolicy);
    //protected static final String random_switch = getKey(NormalRuleSet.RandomResult::isEnable);
    //protected static final String random_result = getKey(NormalRuleSet::getRandomResult);
    //protected static final String random_count = getKey(NormalRuleSet.RandomResult::getCount);
    //protected static final String ENABLE_DEFAULT_RULE = getKey(NormalRuleSet::getEnableDefaultRule);
    //protected static final String NODE_REPETITION_POLICY = getKey(RuleSet::getNodeRepetitionPolicy);
    //protected static final String PROCESS_REPETITION_POLICY = getKey(RuleSet::getProcessRepetitionPolicy);
    //protected static final String DECISION_PROCESS_REPETITION_POLICY = getKey(DecisionRuleSet::getProcessRepetition);
    //protected static final String NODE_ID_LIST = getKey(RuleSet::getNodeIdList);
    //protected static final String RULES = getKey(RuleSet::getRules);
    //protected static final String SPECIAL_RULES = getKey(NormalRuleSet::getSpecialRules);
    //protected static final String DEFAULT_RULE = getKey(NormalRuleSet::getDefaultRule);
    //protected static final String IS_LOOP = getKey(NormalRuleSet::isLoop);
    //protected static final String LOOP_FUNCITON = getKey(NormalRuleSet::getLoopFunction);
    //protected static final String LOOP_COLUMN = getKey(NormalRuleSet::getLoopColumn);
    //protected static final String LOOP_STRATEGY = getKey(RuleSet::getLoopStrategyEnum);
    //protected static final String AUTHORIZATIONS = getKey(RuleSet::getAuthorizations);
    ///**
    // * ----------------------------------Rule-------------------------------
    // */
    //static final String ACTION_VARIABLE = getKey(Rule::getActionVariable);
    //protected static final String RULE_ID = getKey(Rule::getRuleId);
    //protected static final String ORDER_NO = getKey(Rule::getOrderNo);
    //static final String CONDITION_SET = getKey(Rule::getConditionSet);
    //static final String RULE_CONDITION_SET_NAME = getKey(Rule::getPreConditionSetName);
    //static final String PRIORITY = getKey(Rule::getPriority);
    //static final String RULE_TYPE = getKey(Rule::getRuleType);
    //
    //
    ///**
    // * ----------------------------------Other-------------------------------
    // */
    //static final String DATA_TYPE = "dataType";
    //static final String VARIABLE_NAME = getKey(Value::getVariableName);
    ////static final String VARIABLE_CODE = getKey(Value::getVariableCode);
    //static final String OPERATOR_TYPE = "operatorType";
    //static final String PARAMETER_LIST = "parameterList";
    //static final String VALUE = "value";
    //static final String PARAMETERS = "parameters";
    ///**
    // * ----------------------------------Element-------------------------------
    // */
    //static final String ELEMENT_ID = getKey(Element::getElementId);
    ///**
    // * ----------------------------------Variable-------------------------------
    // */
    //static final String VARIABLE_ID = getKey(Variable::getVariableId);
    ///**
    // * ----------------------------------ConditionSet-------------------------------
    // */
    //static final String CONDITION_GROUPS = getKey(ConditionSet::getConditionGroups);
    //static final String CONDITION_SET_ID = getKey(ConditionSet::getId);
    ///**
    // * ----------------------------------ConditionGroup-------------------------------
    // */
    //static final String CONDITION_GROUP_ORDER = getKey(ConditionGroup::getConditionGroupOrder);
    //static final String CONDITIONS = getKey(ConditionGroup::getConditions);
    ///**
    // * ----------------------------------ConditionSetBean.ConditionsBean-------------------------------
    // */
    //static final String CONDITION_ORDER = getKey(NormalCondition::getConditionOrder);
    //static final String CONDITION_ID = getKey(NormalCondition::getConditionId);
    //static final String CONDITION_NAME = getKey(NormalCondition::getConditionName);
    ///**
    // * ----------------------------------ConfigBean-------------------------------
    // */
    //static final String LEFT_VARIABLE = getKey(NormalCondition::getLeftVariable);
    //static final String RIGHT_VARIABLE = getKey(NormalCondition::getRightVariable);
    //
    //static final String BIZ_KEY = getKey(RuleSet::getBizKey);
    //private static final String RULE_SET_ID = getKey(RuleSet::getRuleSetId);
    //private static final String CLASS_NAME = "className";
    //private static final String MULT_RESULT = getKey(MultResult::getMultResult);
    //private static final String EXECUTE = "execute";
    //private static final String BEAN_NAME = "beanName";
    //private static final String CONDITION_GROUP_ID = "conditionGroupId";
    //
    //
    ///**
    // * 主要作用就是ruleset转的json字符串数据再转为ruleset对象
    // *
    // * @param ruleSetJson ruleset转的json字符串
    // * @return RuleSet
    // */
    public void parse(String ruleSetJson, RuleSet ruleSet) {

        //RuleSet engineRuleSet = ruleSet;
        //JSONObject jsonObject = JSON.parseObject(ruleSetJson);
        //try {
        //    String ruleCode = jsonObject.getString(RULE_CODE);
        //    log.info("开始加载ruleSetJson {}", ruleCode);
        //    engineRuleSet.setRuleCode(ruleCode);
        //    engineRuleSet.setBizKey(jsonObject.getString(BIZ_KEY));
        //    engineRuleSet.setRuleSetId(jsonObject.getInteger(RULE_SET_ID));
        //    engineRuleSet.setDescription(jsonObject.getString(DESCRIPTION));
        //    engineRuleSet.setRuleCode(ruleCode);
        //    Boolean isLoop = jsonObject.getBoolean(IS_LOOP);
        //    engineRuleSet.setLoop(isLoop);
        //    if (isLoop) {
        //        loadLoop(engineRuleSet, jsonObject);
        //    }
        //    engineRuleSet.setName(jsonObject.getString(NAME));
        //    engineRuleSet.setNodeRepetitionPolicy(NodeRepetitionEnum.getByName(jsonObject.getString(NODE_REPETITION_POLICY)));
        //    engineRuleSet.setEnableDefaultRule(StatusEnum.getByName(jsonObject.getString(ENABLE_DEFAULT_RULE)));
        //    engineRuleSet.setProcessRepetitionPolicy(StatusEnum.getByName(jsonObject.getString(PROCESS_REPETITION_POLICY)));
        //    //加载 defaultRule
        //    loadDefaultRule(jsonObject, engineRuleSet);
        //    //加载 全流程去重策略
        //    loadProcessRepetitionPolicy(jsonObject, engineRuleSet);
        //    //load rule
        //    loadRule(jsonObject, engineRuleSet);
        //    //load Special Rule
        //    loadSpecialRules(jsonObject, engineRuleSet);
        //    //load Parameters
        //    loadParameters(jsonObject, engineRuleSet);
        //    //加载权限信息
        //    loadAuth(jsonObject,engineRuleSet);
        //} catch (Exception e) {
        //    log.error("{0}", e);
        //    throw new RuleEngineException("RuleSetJson转RuleSet失败");
        //}
    }
    //
    //protected void loadAuth(JSONObject jsonObject, RuleSet engineRuleSet) {
    //    if (jsonObject.containsKey(AUTHORIZATIONS)) {
    //
    //    }
    //
    //
    //
    //};
    //
    //
    //
    //protected void loadLoop(RuleSet engineRuleSet, JSONObject jsonObject) {
    //    JSONObject loopValue = jsonObject.getJSONObject(LOOP_FUNCITON);
    //    String dataType = loopValue.getString(DATA_TYPE);
    //    engineRuleSet.setLoopColumn(jsonObject.getString(LOOP_COLUMN));
    //    engineRuleSet.setLoopStrategyEnum(LoopStrategyEnum.getByName(jsonObject.getString(LOOP_STRATEGY)));
    //    Integer functionParamType = getType(loopValue);
    //    Integer paramVariableId = null;
    //    if (functionParamType.equals(VARIABLE.getStatus())) {
    //        paramVariableId = loopValue.getInteger(VARIABLE_ID);
    //    } else if (functionParamType.equals(ELEMENT.getStatus())) {
    //        paramVariableId = loopValue.getInteger(ELEMENT_ID);
    //    }
    //    engineRuleSet.setLoopFunction(getValue(functionParamType, paramVariableId, loopValue.getString(VALUE), loopValue, DataType.getDataTypeByName(dataType), loopValue.getString(VARIABLE_NAME)));
    //}
    //
    ///**
    // * 测试用力参数
    // *
    // * @param jsonObject    jsonObject
    // * @param engineRuleSet engineRuleSet
    // */
    //private void loadParameters(JSONObject jsonObject, RuleSet engineRuleSet) {
    //    JSONArray jsonArray = jsonObject.getJSONArray(PARAMETERS);
    //    engineRuleSet.setParameters(JSONArray.parseArray(jsonArray.toJSONString(), RuleSet.Parameter.class));
    //}
    //
    ///**
    // * 节点去重策略
    // *
    // * @param jsonObject    jsonObject
    // * @param engineRuleSet engineRuleSet
    // */
    //private void loadProcessRepetitionPolicy(JSONObject jsonObject, RuleSet engineRuleSet) {
    //    if (engineRuleSet instanceof NormalRuleSet) {
    //        engineRuleSet.setProcessRepetitionPolicy(StatusEnum.getByName(jsonObject.getString(PROCESS_REPETITION_POLICY)));
    //        JSONArray jsonArray = jsonObject.getJSONArray(NODE_ID_LIST);
    //        if (CollUtil.isNotEmpty(jsonArray)) {
    //            engineRuleSet.setNodeIdList(jsonArray.stream().map(Object::toString).collect(Collectors.toList()));
    //        }
    //    } else if (engineRuleSet instanceof DecisionRuleSet) {
    //        if (jsonObject.containsKey(DECISION_PROCESS_REPETITION_POLICY)) {
    //            ((DecisionRuleSet) engineRuleSet).setProcessRepetition(JSON.parseObject(jsonObject.getString(DECISION_PROCESS_REPETITION_POLICY), Map.class));
    //        }
    //    }
    //}
    //
    ///**
    // * 特殊规则
    // *
    // * @param jsonObject    jsonObject
    // * @param engineRuleSet engineRuleSet
    // */
    //protected void loadSpecialRules(JSONObject jsonObject, RuleSet engineRuleSet) {
    //}
    //
    //;
    //
    ///**
    // * @param json json
    // * @return Rule
    // */
    //protected Rule getRule(JSONObject json) {
    //    ConditionSet conditionSet = getConditionSet(json.getJSONObject(CONDITION_SET));
    //    Value value = prepareGetValue(json.getJSONObject(ACTION_VARIABLE));
    //    Rule rule = new Rule(conditionSet, value, json.getString(BIZ_KEY), null, json.getString(ORDER_NO));
    //    rule.setPreConditionSetName(JSON.parseArray(json.getString(RULE_CONDITION_SET_NAME),String.class));
    //    return rule;
    //}
    //
    ///**
    // * 普通规则
    // *
    // * @param jsonObject    jsonObject
    // * @param engineRuleSet engineRuleSet
    // */
    //private void loadRule(JSONObject jsonObject, RuleSet engineRuleSet) {
    //    JSONArray jsonArray = jsonObject.getJSONArray(RULES);
    //    sortJSONArray(jsonArray, ORDER_NO);
    //    for (Object o : jsonArray) {
    //        JSONObject json = (JSONObject) o;
    //        Rule rule = getRule(json);
    //        rule.setIsSpecial(false);
    //        rule.setPriority(json.getInteger(PRIORITY));
    //        rule.setRuleId(json.getInteger(RULE_ID));
    //        engineRuleSet.addRule(rule);
    //    }
    //}
    //
    ///**
    // * 条件
    // *
    // * @param defaultRule defaultRule
    // * @return ConditionSet
    // */
    //private ConditionSet getConditionSet(JSONObject defaultRule) {
    //    ConditionSet conditionSet = new ConditionSet();
    //    JSONArray conditionSetNews = defaultRule.getJSONArray(CONDITION_GROUPS);
    //    if (CollUtil.isEmpty(conditionSetNews)) {
    //        return conditionSet;
    //    }
    //    conditionSet.setId(defaultRule.getLong(CONDITION_SET_ID));
    //    sortJSONArray(conditionSetNews, CONDITION_GROUP_ORDER);
    //    for (Object o : conditionSetNews) {
    //        JSONObject conditionSetNew = (JSONObject) o;
    //        ConditionGroup engineConditionGroup = new ConditionGroup();
    //        engineConditionGroup.setConditionGroupId(conditionSetNew.getInteger(CONDITION_GROUP_ID));
    //        engineConditionGroup.setConditionGroupOrder(conditionSetNew.getInteger(CONDITION_GROUP_ORDER));
    //        JSONArray jsonArray = conditionSetNew.getJSONArray(CONDITIONS);
    //        for (Object o1 : jsonArray) {
    //            JSONObject conditionNew = (JSONObject) o1;
    //            String symbolType = conditionNew.getString(OPERATOR_TYPE);
    //            DataType dataType = Symbol.getSymbolByEnumName(symbolType).getType();
    //            if (Objects.isNull(dataType)) {
    //                throw new IllegalArgumentException(String.format("暂不支持%s", symbolType));
    //            }
    //            //对左值和右值而言，根据类型，分别进行取值，取ID
    //            JSONObject leftVariable = conditionNew.getJSONObject(LEFT_VARIABLE);
    //            RuleActIdValue leftVariableBean = getRuleActIdValue(leftVariable);
    //            JSONObject rightVariable = conditionNew.getJSONObject(RIGHT_VARIABLE);
    //            RuleActIdValue rightVariableBean = getRuleActIdValue(rightVariable);
    //            //获取左右值，建立条件
    //            Value leftValue = getValue(leftVariableBean.getType(), leftVariableBean.getRuleActId(),
    //                    leftVariableBean.getFixedValue(), leftVariable, DataType.getDataTypeByName(leftVariable.getString(DATA_TYPE)), null);
    //            Symbol symbol = Symbol.getSymbolByEnumName(symbolType);
    //            Value rightValue = getValue(rightVariableBean.getType(), rightVariableBean.getRuleActId(),
    //                    rightVariableBean.getFixedValue(), rightVariable, DataType.getDataTypeByName(rightVariable.getString(DATA_TYPE)), null);
    //            NormalCondition normalCondition = new NormalCondition(leftValue, symbol, rightValue);
    //            normalCondition.setConditionName(conditionNew.getString(CONDITION_NAME));
    //            normalCondition.setConditionId(conditionNew.getInteger(CONDITION_ID));
    //            normalCondition.setConditionOrder(conditionNew.getInteger(CONDITION_ORDER));
    //            engineConditionGroup.addCondition(normalCondition);
    //        }
    //        conditionSet.addConditionGroups(engineConditionGroup);
    //    }
    //    return conditionSet;
    //}
    //
    ///**
    // * 加载默认规则
    // *
    // * @param jsonObject    jsonObject
    // * @param engineRuleSet engineRuleSet
    // */
    //private void loadDefaultRule(JSONObject jsonObject, RuleSet engineRuleSet) {
    //    JSONObject defaultRule = jsonObject.getJSONObject(DEFAULT_RULE);
    //    if (Objects.isNull(defaultRule)) {
    //        return;
    //    }
    //    Value value = prepareGetValue(defaultRule.getJSONObject(ACTION_VARIABLE));
    //    ConditionSet conditionSet = getConditionSet(defaultRule.getJSONObject(CONDITION_SET));
    //    Rule rule = new Rule(conditionSet, value, jsonObject.getString(BIZ_KEY), defaultRule.getString(VARIABLE_NAME), null);
    //    rule.setRuleId(defaultRule.getInteger(RULE_ID));
    //    engineRuleSet.setDefaultRule(rule);
    //}
    //
    ///**
    // * 根据对应的规则获取必要的参数
    // *
    // * @param variableJSONObject actionVariable
    // * @return v
    // */
    //private Value prepareGetValue(JSONObject variableJSONObject) {
    //    if (MultResult.class.getName().equals(variableJSONObject.getString(CLASS_NAME))) {
    //        MultResult value = new MultResult();
    //        JSONObject multResult = variableJSONObject.getJSONObject(MULT_RESULT);
    //        for (Map.Entry<String, Object> entry : multResult.entrySet()) {
    //            JSONObject result = (JSONObject) entry.getValue();
    //            RuleActIdValue ruleActIdValue = getRuleActIdValue(result);
    //            String dataType = result.getString(DATA_TYPE);
    //            value.putResult(entry.getKey(), getValue(ruleActIdValue.getType(), ruleActIdValue.getRuleActId(), ruleActIdValue.getFixedValue(), result, DataType.getDataTypeByName(dataType), null));
    //        }
    //        return value;
    //    }
    //    RuleActIdValue ruleActIdValue = getRuleActIdValue(variableJSONObject);
    //    String dataType = variableJSONObject.getString(DATA_TYPE);
    //    return getValue(ruleActIdValue.getType(), ruleActIdValue.getRuleActId(), ruleActIdValue.getFixedValue(), variableJSONObject, DataType.getDataTypeByName(dataType), null);
    //}
    //
    ///**
    // * 递归获取值
    // *
    // * @param valueType    getType()
    // * @param valueIds     e v id
    // * @param fixedValue   固定值
    // * @param rule         数据
    // * @param dataType     数据类型
    // * @param constantCode constantCode
    // * @return Value
    // */
    //protected Value getValue(Integer valueType, Integer valueIds, String fixedValue, JSONObject rule, DataType dataType, String constantCode) {
    //    VariableTypeEnum variableTypeEnum = Optional.ofNullable(VariableTypeEnum.getStatus(valueType)).orElseThrow(
    //            () -> new RuleEngineException("不存在的变量类型"));
    //    switch (variableTypeEnum) {
    //        case VARIABLE:
    //            //根据id查询变量
    //            String bizKey = rule.getString(BIZ_KEY);
    //            JSONObject executeJson = rule.getJSONObject(EXECUTE);
    //            //没有EXECUTE.BEAN_NAME 就是固定值变量
    //            if (executeJson == null || executeJson.getString(BEAN_NAME) == null) {
    //                //固定值变量
    //                String value = rule.getString(VALUE);
    //                Variable variable = new Variable(null, null, rule.getString(VARIABLE_NAME), bizKey, value,
    //                        DataType.getDataTypeByName(rule.getString(DATA_TYPE)));
    //                variable.setVariableId(valueIds);
    //                return variable;
    //            } else {
    //                JSONObject parameterList = rule.getJSONObject(PARAMETER_LIST);
    //                Map<String, Value> paramList = null;
    //                if (CollUtil.isNotEmpty(parameterList)) {
    //                    paramList = new HashMap<>(parameterList.size(), 1);
    //                    for (Map.Entry<String, Object> map : parameterList.entrySet()) {
    //                        //k code
    //                        String k = map.getKey();
    //                        JSONObject vNew = (JSONObject) map.getValue();
    //                        if (vNew.containsKey(CLASS_NAME)) {
    //                            Integer functionParamType = getType(vNew);
    //                            Integer paramVariableId = null;
    //                            if (functionParamType.equals(VARIABLE.getStatus())) {
    //                                paramVariableId = vNew.getInteger(VARIABLE_ID);
    //                            } else if (functionParamType.equals(ELEMENT.getStatus())) {
    //                                paramVariableId = vNew.getInteger(ELEMENT_ID);
    //                            }
    //                            Value param = getValue(functionParamType, paramVariableId, vNew.getString(VALUE),
    //                                    vNew, DataType.getDataTypeByName(vNew.getString(DATA_TYPE)), k);
    //                            paramList.put(k, param);
    //                        }
    //                    }
    //                }
    //                String beanName = executeJson.getString(BEAN_NAME);
    //                if (Validator.isEmpty(beanName)) {
    //                    log.error("函数名为空");
    //                    throw new RuleEngineException("函数名为空");
    //                }
    //                Execute execute = getFunction(beanName);
    //                execute.setBeanName(beanName);
    //                String variableCode = rule.getString(VARIABLE_NAME);
    //
    //                Variable variable = new Variable(paramList, execute, variableCode, bizKey, null,
    //                        DataType.getDataTypeByName(rule.getString(DATA_TYPE)));
    //                variable.setVariableId(valueIds);
    //                variable.setVariableName(rule.getString(VARIABLE_NAME));
    //                variable.setClassName(variable.getClass().getName());
    //                return variable;
    //            }
    //        case RESULT:
    //            return new Result(null, constantCode);
    //        case ELEMENT:
    //            String variableCode = rule.getString(VARIABLE_NAME);
    //            if (StringUtils.isBlank(variableCode)) {
    //                variableCode = constantCode;
    //            }
    //            Element element1 = new Element(variableCode, DataType.getDataTypeByName(rule.getString(DATA_TYPE)), rule.getString(BIZ_KEY));
    //            element1.setVariableName(rule.getString(VARIABLE_NAME));
    //            element1.setElementId(valueIds);
    //            return element1;
    //        case CONSTANT:
    //            return new Constant(constantCode, dataType, null, fixedValue);
    //        default:
    //            throw new RuleEngineException(String.format("错误的类型%s", variableTypeEnum));
    //    }
    //}
    //
    //public abstract Execute getFunction(String functionBeanName);
    //
    ///**
    // * CONSTANT(0),
    // * VARIABLE(1),
    // * ELEMENT(2),
    // * RESULT(3);
    // *
    // * @param jsonObject className
    // * @return int
    // */
    //protected static int getType(JSONObject jsonObject) {
    //    String className = jsonObject.getString(CLASS_NAME);
    //    if (Constant.class.getName().equals(className)) {
    //        return 0;
    //    } else if (Variable.class.getName().equals(className)) {
    //        return 1;
    //    } else if (Element.class.getName().equals(className)) {
    //        return 2;
    //    } else if (Result.class.getName().equals(className)) {
    //        return 3;
    //    } else if (MultResult.class.getName().equals(className)) {
    //        return 4;
    //    } else {
    //        throw new RuleEngineException(String.format("不支持的类型%s", className));
    //    }
    //}
    //
    ///**
    // * 根据RuleActIdValue 获取ruleActId/fixedValue/type
    // *
    // * @param variableJSONObject variableJSONObject
    // * @return RuleActIdValue
    // */
    //private RuleActIdValue getRuleActIdValue(JSONObject variableJSONObject) {
    //    RuleActIdValue ruleActIdValue = new RuleActIdValue();
    //    Integer type = getType(variableJSONObject);
    //    if (type.equals(CONSTANT.getStatus())) {
    //        ruleActIdValue.setFixedValue(variableJSONObject.getString(VALUE));
    //    } else {
    //        if (type.equals(ELEMENT.getStatus())) {
    //            ruleActIdValue.setRuleActId(variableJSONObject.getInteger(ELEMENT_ID));
    //        } else {
    //            ruleActIdValue.setRuleActId(variableJSONObject.getInteger(VARIABLE_ID));
    //        }
    //    }
    //    ruleActIdValue.setType(type);
    //    return ruleActIdValue;
    //}
    //
    ///**
    // * jsonArray 排序
    // *
    // * @param jsonArray       jsonArray
    // * @param orderColumnName 根据哪个字段排序
    // */
    //protected void sortJSONArray(JSONArray jsonArray, String orderColumnName) {
    //    //如果是个null，或者空集合，不排序
    //    if (CollUtil.isEmpty(jsonArray)) {
    //        return;
    //    }
    //    //如果元素只有一个，不需要排序
    //    if (jsonArray.size() == 1) {
    //        return;
    //    }
    //    jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getInteger(orderColumnName)));
    //}
    //
    //@Data
    //private static class RuleActIdValue {
    //    private Integer ruleActId;
    //    private String fixedValue;
    //    private Integer type;
    //}
}
