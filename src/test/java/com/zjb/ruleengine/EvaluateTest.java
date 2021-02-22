package com.zjb.ruleengine;

import cn.hutool.core.collection.CollectionUtil;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;


public class EvaluateTest {

    @Test
    public void t() {
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("result001", list);
        list.add(map);
        System.out.println();
    }

    @Test
    public void execute() throws Exception {
        String tempString  = "node/123/456,node1/789/null";
        List<String> nodeIdList = new ArrayList<>();
        List<String> results = Arrays.asList("123","789","456","000");
        /**
         * 如果有效审批人是空，不进行去重
         */
            String approveString = tempString + "";
            List<String> approveList = Arrays.asList(approveString.split(","));
            /**
             * 如果有效审批人是空字符串，转成list为空list不进行去重
             */
            if(CollectionUtil.isNotEmpty(approveList)) {
                /**
                 * 如果去重节点为空，表示所有节点都去重。
                 */
                List<String> deletePerson = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(nodeIdList)) {
                    for (String approve : approveList) {
                        String[] info = approve.split("/");
                        String nodeId = info[0];
                        if (nodeIdList.contains(nodeId)) {
                            if (info[2].equals("null")) {
                                deletePerson.add(info[1]);
                            } else {
                                deletePerson.add(info[2]);
                            }
                        }
                    }
                } else {
                    /**
                     * 如果去重节点不为空，表示对列表中的节点去重。
                     */
                    for (String approve : approveList) {
                        String[] info = approve.split("/");
                        if (info[2].equals("null")) {
                            deletePerson.add(info[1]);
                        } else {
                            deletePerson.add(info[2]);
                        }
                    }
                }
                results = (List) results.stream().filter(e -> {
                    String tempStr = (String) e;
                    return !deletePerson.contains(tempStr);
                }).collect(Collectors.toList());
                System.out.println(results);
            }
    }
}
