package com.learning.sqlformat;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by xuechongyang on 17/8/23.
 */
public class FormatData {

    @Test
    public void format() {
        try {
            String filePath = FormatData.class.getClassLoader().getResource("data").getPath();
            String data = FileUtils.readFileToString(new File(filePath));
            List<DataModel> list = JSONObject.parseObject(data, new TypeReference<List<DataModel>>(){});
            list.forEach(e -> System.out.println(e.getCampaign_id()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class DataModel {
        private Integer deal_id;
        private Integer minus;
        private Integer campaign_id;
    }
}
