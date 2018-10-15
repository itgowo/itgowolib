package com.itgowo.itgowodemo;

import com.google.gson.Gson;
import com.itgowo.Http.HttpClient;
import com.itgowo.Http.Method;
import com.itgowo.Http.Response;
import com.itgowo.Http.onCallbackListener;

public class Test {
    public static void testHttpSync(){
        try {
            Response response= HttpClient.RequestSync("http://10.1.100.171:16671", Method.POST, new Gson().toJson(new Entity().setAge(22).setName("aaaadfa")));
            System.out.println(response.getBodaStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testHttp(){
        try {
            String json=new Gson().toJson(new Entity().setAge(22).setName("aaaadfa"));
            HttpClient.Request("http://10.1.100.171:16671", Method.POST, json, false, new onCallbackListener() {

                @Override
                public void onError(Response response, Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onSuccess(Response response) {
                    System.out.println("ThreadName:"+Thread.currentThread().getName());
                    System.out.println(response.getBodaStr());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

