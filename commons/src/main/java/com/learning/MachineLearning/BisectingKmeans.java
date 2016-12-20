/** 
 * File: BisectingKmeans.java
 * Copyright (C), 2015-2016 中盈优创  Tech.Co.Ltd.All Rights Reserved.
 */
package com.learning.MachineLearning;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 二分k均值，实际上是对一个集合做多次的k=2的kmeans划分， 每次划分后会对sse值较大的簇再进行二分。 最终使得或分出来的簇的个数为k个则停止
 * 
 * 这里利用kmeans的java实现作为基础类。
 */
public class BisectingKmeans {
    private int k;// 分成多少簇

    private List<float[]> dataSet;// 当前要被二分的簇

    private List<ClusterSet> cluster; // 簇

    public BisectingKmeans(int k) {
        if (k < 2) {
            k = 2;
        }
        this.k = k;
    }

    /**
     * 设置需分组的原始数据集
     * 
     * @param dataSet
     */
    public void setDataSet(List<float[]> dataSet) {
        this.dataSet = dataSet;
    }

    public List<List<float[]>> getCluster() {
        List<List<float[]>> result = Lists.newArrayList();
        for (int i = 0; i < cluster.size(); i++) {
            result.add(cluster.get(i).getClu());
        }
        return result;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        System.out.println("BisectingKmeans begins");
        biKmeans();
        long endTime = System.currentTimeMillis();
        System.out.println("BisectingKmeans ends, running time=" + (endTime - startTime) + "ms");
    }

    /**
     * 初始化
     */
    private void init() {
        int dataSetLength = dataSet.size();
        if (k > dataSetLength) {
            k = dataSetLength;
        }
    }

    /**
     * Kmeans算法核心过程方法
     */
    private void biKmeans() {
        init();
        // 调用kmeans进行二分
        cluster = new ArrayList<ClusterSet>();

        while (cluster.size() < k) {
            List<ClusterSet> clu = kmeans(dataSet);
            cluster.addAll(clu);
            if (cluster.size() == k) {
                break;
            } else {// 顺序计算他们的误差平方和
                float maxErro = 0f;
                int maxSetIndex = 0;
                int i = 0;
                for (ClusterSet tt : cluster) {
                    //计算误差平方和并得出误差平方和最大的簇
                    float erroe = CommonUtil.countRule(tt.getClu(), tt.getCenter());
                    tt.setErro(erroe);

                    if (maxErro < erroe) {
                        maxErro = erroe;
                        maxSetIndex = i;
                    }
                    i++;
                }
                dataSet = cluster.get(maxSetIndex).getClu();
                cluster.remove(maxSetIndex);
            }
        }
    }

    /**
     * 调用kmeans得到两个簇。
     * 
     * @param dataSet
     * @return
     */
    private List<ClusterSet> kmeans(List<float[]> dataSet) {
        Kmeans kmeans = new Kmeans(2);
        // 设置原始数据集
        kmeans.setDataSet(dataSet);
        // 执行算法
        kmeans.execute();
        // 得到聚类结果
        List<List<float[]>> clus = kmeans.getCluster();
        List<ClusterSet> clusterset = new ArrayList<ClusterSet>();

        int i = 0;
        for (List<float[]> cl : clus) {
            ClusterSet cs = new ClusterSet();
            cs.setClu(cl);
            cs.setCenter(kmeans.getCenter().get(i));
            clusterset.add(cs);
            i++;
        }
        return clusterset;
    }

    class ClusterSet {
        private float erro;

        private List<float[]> clu;

        private float[] center;

        public float getErro() {
            return erro;
        }

        public void setErro(float erro) {
            this.erro = erro;
        }

        public List<float[]> getClu() {
            return clu;
        }

        public void setClu(List<float[]> clu) {
            this.clu = clu;
        }

        public float[] getCenter() {
            return center;
        }

        public void setCenter(float[] center) {
            this.center = center;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 初始化一个Kmean对象，将k置为10
        BisectingKmeans bkm = new BisectingKmeans(4);
        // 初始化试验集
        ArrayList<float[]> dataSet = new ArrayList<float[]>();

        dataSet.add(new float[] {11, 21, 11});
        dataSet.add(new float[] {10, 20, 10});
        dataSet.add(new float[] {10, 20, 10});
        dataSet.add(new float[] {10, 20, 10});
        dataSet.add(new float[] {10, 20, 10});
        dataSet.add(new float[] {10, 20, 10});
        dataSet.add(new float[] {4, 3, 3});
        dataSet.add(new float[] {4, 3, 3});
        dataSet.add(new float[] {4, 3, 3});
        dataSet.add(new float[] {4, 3, 3});
        dataSet.add(new float[] {4, 3, 3});
        dataSet.add(new float[] {4, 3, 3});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {1, 9, 1});
        dataSet.add(new float[] {6, 5, 6});
        dataSet.add(new float[] {6, 5, 6});
        dataSet.add(new float[] {6, 5, 6});
        dataSet.add(new float[] {6, 5, 6});
        dataSet.add(new float[] {6, 5, 6});
        // 设置原始数据集
        bkm.setDataSet(dataSet);
        // 执行算法
        bkm.execute();
        // 得到聚类结果
        List<List<float[]>> cluster = bkm.getCluster();
        // 查看结果
        for (List<float[]> list : cluster) {
            CommonUtil.printDataArray(list, "cluster");
        }

    }
}
