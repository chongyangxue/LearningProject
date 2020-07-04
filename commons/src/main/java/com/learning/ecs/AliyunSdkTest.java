package com.learning.ecs;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.aliyuncs.vpc.model.v20160428.*;
import org.junit.Test;


public class AliyunSdkTest {

    @Test
    public void test() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou","bojY1KZ0GH4nJVG9", "tyqWXoNbK872fYHdCEPEO7rogDaPsI");
        IAcsClient client = new DefaultAcsClient(profile);

        CreateVpcRequest request = new CreateVpcRequest();
        request.setRegionId("cn-hangzhou");
        request.setCidrBlock("192.168.0.0/16");

        try {
            CreateVpcResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }
}
