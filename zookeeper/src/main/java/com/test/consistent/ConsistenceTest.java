package com.test.consistent;

/**
 * ZKĬ��֧�ֵ�������һ���ԣ���leaderд�����ʱ��follower�п��ܶ����ɵ�����
 * ���Ҫ��֤ǿһ���ԣ���Ҫ�ȵ���sync����
 */
public class ConsistenceTest {
    public static void main(String[] args) {
        ZkClient leader = new ZkClient("192.168.6.208:2181");
        ZkClient follow1 = new ZkClient("192.168.6.207:2181");
        ZkClient follow2 = new ZkClient("192.168.6.143:2181");
        
        String path = "/test";
        for (int i = 0; i < 10000; i++) {
            String value = "test" + i;
            leader.update(path, value);
//            follow1.sync(path);
//            follow2.sync(path);
            String value1 = follow1.getData(path);
            String value2 = follow2.getData(path);
            if (!value.equals(value1) || !value.equals(value2)) {
                System.out.println(
                        String.format("�������ݲ�һ��,value:%s, value1:%s, value2:%s", value, value1, value2));
            }
        }
    }
}
