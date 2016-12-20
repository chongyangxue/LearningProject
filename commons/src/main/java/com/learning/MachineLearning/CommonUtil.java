/** 
 * File: CommonUtil.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.MachineLearning;

import java.util.List;

/**
 * Description: Author: Sachiel Date: 2016年5月26日
 */
public class CommonUtil {

    /**
     * 计算两个点之间的距离
     * 
     * @param element
     *            点1
     * @param center
     *            点2
     * @return 距离
     */
    public static float distance(float[] element, float[] center) {
        float distance = 0.0f;
        for (int i = 0; i < element.length; i++) {
            distance += Math.pow(element[i] - center[i], 2);
        }
        distance = (float) Math.sqrt(distance);
        return distance;
    }

    /**
     * 求两点误差平方的方法
     * 
     * @param element
     *            点1
     * @param center
     *            点2
     * @return 误差平方
     */
    public static float errorSquare(float[] element, float[] center) {
        float errSquare = 0f;
        for (int i = 0; i < element.length; i++) {
            errSquare += Math.pow(element[i] - center[i], 2);
        }
        return errSquare;
    }

    /**
     * 计算误差平方和准则函数方法
     */
    public static float countRule(List<float[]> cluster, float[] center) {
        float jcF = 0;
        for (int j = 0; j < cluster.size(); j++) {
            jcF += CommonUtil.errorSquare(cluster.get(j), center);
        }
        return jcF;
    }

    /**
     * 打印数据，测试用
     * 
     * @param dataArray
     *            数据集
     * @param dataArrayName
     *            数据集名称
     */
    public static void printDataArray(List<float[]> dataArray, String dataArrayName) {
        for (int i = 0; i < dataArray.size(); i++) {
            System.out.print(dataArrayName + ": ");
            for (int k = 0; k < dataArray.get(i).length; k++) {
                System.out.print(dataArray.get(i)[k] + " ");
            }
            System.out.println();
        }
        System.out.println("===================================");
    }
}
