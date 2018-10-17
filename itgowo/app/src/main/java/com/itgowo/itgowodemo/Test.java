package com.itgowo.itgowodemo;

import com.google.gson.Gson;
import com.itgowo.http.HttpClient;
import com.itgowo.http.Method;
import com.itgowo.http.HttpResponse;
import com.itgowo.http.onCallbackListener;

public class Test {
    public static void testHttpSync(){
        try {
            HttpResponse response= HttpClient.RequestSync("http://10.1.100.171:16671", Method.POST, new Gson().toJson(new Entity().setAge(22).setName("aaaadfa")));
            System.out.println(response.getBodyStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testHttp(){
        try {
            String json=new Gson().toJson(new Entity().setAge(22).setName("aaaadfa"));
            HttpClient.Request("http://10.1.100.171:16671", Method.POST, json, false, new onCallbackListener() {

                @Override
                public void onError(HttpResponse response, Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void onSuccess(HttpResponse response) {
                    System.out.println("ThreadName:"+Thread.currentThread().getName());
                    System.out.println(response.getBodyStr());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

