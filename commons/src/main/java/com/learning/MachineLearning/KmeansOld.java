/** 
 * File: kmeans.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.MachineLearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * K均值聚类算法 
 */
public class KmeansOld {
    private int k;// 分成多少簇  

    private int iteratorCount;// 迭代次数  

    private int dataSetLength;// 数据集元素个数，即数据集的长度  

    private List<float[]> dataSet;// 数据集链表  

    private List<float[]> center;// 中心链表  

    private List<List<float[]>> cluster; // 簇  

    private ArrayList<Float> jc;// 误差平方和，k越接近dataSetLength，误差越小  

    private Random random;

    /**
     * 设置需分组的原始数据集
     * 
     * @param dataSet
     */
    public void setDataSet(List<float[]> dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * 获取结果分组
     * 
     * @return 结果集
     */
    public List<List<float[]>> getCluster() {
        return cluster;
    }

    /**
     * 构造函数，传入需要分成的簇数量
     * 
     * @param k
     *            簇数量,若k<=0时，设置为1，若k大于数据源的长度时，置为数据源的长度
     */
    public KmeansOld(int k) {
        if (k <= 0) {
            k = 1;
        }
        this.k = k;
    }

    /**
     * 初始化
     */
    private void init() {
        iteratorCount = 0;
        random = new Random();
        if (dataSet == null || dataSet.size() == 0) {
            System.err.println("dataSet is null");
            System.exit(1);
        }
        dataSetLength = dataSet.size();
        if (k > dataSetLength) {
            k = dataSetLength;
        }
        center = initCenters();
        cluster = initCluster();
        jc = new ArrayList<Float>();
    }

    /**
     * 初始化中心数据链表，分成多少簇就有多少个中心点
     * 
     * @return 中心点集
     */
    private List<float[]> initCenters() {
        List<float[]> center = new ArrayList<float[]>();
        int[] randoms = new int[k];
        boolean flag;
        int temp = random.nextInt(dataSetLength);
        randoms[0] = temp;
        for (int i = 1; i < k; i++) {
            flag = true;
            while (flag) {
                temp = random.nextInt(dataSetLength);
                int j = 0;
                while (j < i) {
                    if (temp == randoms[j]) {
                        break;
                    }
                    j++;
                }
                if (j == i) {
                    flag = false;
                }
            }
            randoms[i] = temp;
        }

        // 测试随机数生成情况  
        // for(int i=0;i<k;i++)  
        // {  
        // System.out.println("test1:randoms["+i+"]="+randoms[i]);  
        // }  

        for (int i = 0; i < k; i++) {
            center.add(dataSet.get(randoms[i]));// 生成初始化中心链表  
        }
        return center;
    }

    /**
     * 初始化簇集合
     * 
     * @return 一个分为k簇的空数据的簇集合
     */
    private List<List<float[]>> initCluster() {
        List<List<float[]>> cluster = new ArrayList<List<float[]>>();
        for (int i = 0; i < k; i++) {
            cluster.add(new ArrayList<float[]>());
        }
        return cluster;
    }

    /**
     * 计算两个点之间的距离
     * 
     * @param element
     *            点1
     * @param center
     *            点2
     * @return 距离
     */
    private float distance(float[] element, float[] center) {
        float distance = 0.0f;
        float x = element[0] - center[0];
        float y = element[1] - center[1];
        float z = x * x + y * y;
        distance = (float) Math.sqrt(z);

        return distance;
    }

    /**
     * 获取距离集合中最小距离的位置
     * 
     * @param distance
     *            距离数组
     * @return 最小距离在距离数组中的位置
     */
    private int minDistance(float[] distance) {
        float minDistance = distance[0];
        int minLocation = 0;
        for (int i = 1; i < distance.length; i++) {
            if (distance[i] < minDistance) {
                minDistance = distance[i];
                minLocation = i;
            } else if (distance[i] == minDistance) {// 如果相等，随机返回一个位置  
                if (random.nextInt(10) < 5) {
                    minLocation = i;
                }
            }
        }

        return minLocation;
    }

    /**
     * 核心，将当前元素放到最小距离中心相关的簇中
     */
    private void clusterSet() {
        float[] distance = new float[k];
        for (int i = 0; i < dataSetLength; i++) {
            for (int j = 0; j < k; j++) {
                distance[j] = distance(dataSet.get(i), center.get(j));
            }
            int minLocation = minDistance(distance);
            cluster.get(minLocation).add(dataSet.get(i));// 核心，将当前元素放到最小距离中心相关的簇中  
        }
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
    private float errorSquare(float[] element, float[] center) {
        float x = element[0] - center[0];
        float y = element[1] - center[1];

        float errSquare = x * x + y * y;

        return errSquare;
    }

    /**
     * 计算误差平方和准则函数方法
     */
    private void countRule() {
        float jcF = 0;
        for (int i = 0; i < cluster.size(); i++) {
            for (int j = 0; j < cluster.get(i).size(); j++) {
                jcF += errorSquare(cluster.get(i).get(j), center.get(i));
            }
        }
        jc.add(jcF);
    }

    /**
     * 设置新的簇中心方法
     */
    private void setNewCenter() {
        for (int i = 0; i < k; i++) {
            int n = cluster.get(i).size();
            if (n != 0) {
                float[] newCenter = {0, 0};
                for (int j = 0; j < n; j++) {
                    newCenter[0] += cluster.get(i).get(j)[0];
                    newCenter[1] += cluster.get(i).get(j)[1];
                }
                // 设置一个平均值  
                newCenter[0] = newCenter[0] / n;
                newCenter[1] = newCenter[1] / n;
                center.set(i, newCenter);
            }
        }
    }

    /**
     * 打印数据，测试用
     * 
     * @param dataArray
     *            数据集
     * @param dataArrayName
     *            数据集名称
     */
    public void printDataArray(List<float[]> dataArray, String dataArrayName) {
        for (int i = 0; i < dataArray.size(); i++) {
            System.out.println("print:" + dataArrayName + "[" + i + "]={" + dataArray.get(i)[0] + ","
                    + dataArray.get(i)[1] + "}");
        }
        System.out.println("===================================");
    }

    /**
     * Kmeans算法核心过程方法
     */
    private void kmeans() {
        init();
        // 循环分组，直到误差不变为止  
        while (true) {
            clusterSet();
            countRule();
            // 误差不变了，分组完成  
            if (iteratorCount != 0) {
                if (jc.get(iteratorCount) - jc.get(iteratorCount - 1) == 0) {
                    break;
                }
            }
            setNewCenter();
            iteratorCount++;
            cluster.clear();
            cluster = initCluster();
        }
    }
    
    public List<float[]> getCenter() {
        return center;
    }

    public void setCenter(List<float[]> center) {
        this.center = center;
    }

    /**
     * 执行算法
     */
    public void execute() {
        long startTime = System.currentTimeMillis();
        System.out.println("kmeans begins");
        kmeans();
        long endTime = System.currentTimeMillis();
        System.out.println("kmeans running time=" + (endTime - startTime) + "ms");
        System.out.println("kmeans ends");
        System.out.println();
    }

    public static void main(String[] args) {
        KmeansOld kmeans = new KmeansOld(3);
        List<float[]> dataSet = new ArrayList<float[]>();
        dataSet.add(new float[] {1, 2, 1});
        dataSet.add(new float[] {3, 3, 1});
        dataSet.add(new float[] {3, 4, 1});
        dataSet.add(new float[] {5, 6, 1});
        dataSet.add(new float[] {8, 9, 1});
        dataSet.add(new float[] {4, 5, 1});
        dataSet.add(new float[] {6, 4, 1});
        dataSet.add(new float[] {3, 9, 1});
        dataSet.add(new float[] {5, 9, 1});
        dataSet.add(new float[] {4, 2, 1});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {7, 8, 1});
        //设置原始数据集  
        kmeans.setDataSet(dataSet);
        //执行算法  
        kmeans.execute();
        //得到聚类结果  
        List<List<float[]>> cluster = kmeans.getCluster();
        //查看结果  
        for (int i = 0; i < cluster.size(); i++) {
            kmeans.printDataArray(cluster.get(i), "cluster[" + i + "]");
        }

    }
}
