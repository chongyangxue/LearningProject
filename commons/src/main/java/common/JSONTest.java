package common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * Created by xuechongyang on 16/7/28.
 */
public class JSONTest {

    @Test
    public void testJSONFormat() {
        JSONArray array = new JSONArray();
        array.add(1);
        array.add(2);
        array.add(3);
        JSONObject data = new JSONObject();

        data.put("productName", "name");
        data.put("dealList", array);

        JSONArray result = new JSONArray();
        result.add(data);

        System.out.println(result);
    }
}
