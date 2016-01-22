package com.test.consistent;

/**
 * ZK默认支持的是最终一致性，往leader写入更新时，follower有可能读到旧的数据
 * 如果要保证强一致性，需要先调用sync方法
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
                        String.format("发现数据不一致,value:%s, value1:%s, value2:%s", value, value1, value2));
            }
        }
    }
}
