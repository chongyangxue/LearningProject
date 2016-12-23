package com.learning.Serialize;


import com.meituan.cache.redisCluster.client.transcoders.spyTranscoders.CachedData;
import com.meituan.cache.redisCluster.client.transcoders.spyTranscoders.SerializingTranscoder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: YangXuehua
 * Date: 13-5-7
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public class SpyMemcachedSerialize {

    private static final String FILE_PATH = "/opt/serialize/spymemcached";

    public static void write(Object o) throws Exception {
        CachedData cachedData = new SerializingTranscoder().encode(o);
        byte[] data = cachedData.getData();
        int spyFlag = cachedData.getFlag();
        byte[] ret = new byte[data.length + 1];
        System.arraycopy(data, 0, ret, 1, data.length);
        byte flag = (byte) (spyFlag >> 8);

        ret[0] = flag;
        FileUtils.writeByteArrayToFile(new File(FILE_PATH), ret);;
    }

    @Test
    public void read() throws Exception {
        byte[] bytes = FileUtils.readFileToByteArray(new File(FILE_PATH));
        int flag = 0;
        flag = (bytes[0] & 0x000000ff) << 8;

        byte[] data = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, data, 0, data.length);

        CachedData cachedData = new CachedData(flag, data);
        Object o = new SerializingTranscoder().decode(cachedData);
        System.out.println(o.toString());;
    }
}
