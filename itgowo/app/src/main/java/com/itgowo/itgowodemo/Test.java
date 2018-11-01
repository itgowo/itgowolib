package com.itgowo.itgowodemo;

import android.os.Environment;

import com.google.gson.Gson;
import com.itgowo.http.HttpClient;
import com.itgowo.http.Method;
import com.itgowo.http.HttpResponse;
import com.itgowo.http.onCallbackListener;
import com.itgowo.http.onDownloadFileCallbackListener;
import com.itgowo.http.onUploadFileCallbackListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void testHttpSync(){
        try {
            HttpResponse response= HttpClient.RequestSync("http://10.1.100.171:16671", Method.POST,null, new Gson().toJson(new Entity().setAge(22).setName("aaaadfa")));
            System.out.println(response.getBodyStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void testHttp(){
        try {
            String json=new Gson().toJson(new Entity().setAge(22).setName("aaaadfa"));
            HttpClient.Request("http://10.1.100.171:16671", Method.POST, null,json, false, new onCallbackListener() {

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
    public static void testDownloadFile(){
        File file=Environment.getExternalStorageDirectory();
        file=new File(file,"test");
        file.mkdirs();
        HttpClient.downloadFile("http://file.itgowo.com/itgowo/MiniHttpServer/web_app.zip", file.getAbsolutePath(), false, new onDownloadFileCallbackListener() {
            @Override
            public void onError(HttpResponse response, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onProcess(String file, int countBytes, int processBytes) {
                System.out.println("file = [" + file + "], countBytes = [" + countBytes + "], processBytes = [" + processBytes + "]  "+processBytes*100/countBytes);
            }

            @Override
            public void onDownloadFileSuccess(String file) {
                System.out.println("file = [" + file + "]");
            }
        });
    }
    public static void testUpload(){
        List<String> files=new ArrayList<>();
        files.add("/storage/emulated/0/test/ver.txt");
        HttpClient.uploadFiles("http://10.1.101.117:12111", files, false, new onUploadFileCallbackListener() {
            @Override
            public void onProcess(String file, int countBytes, int processBytes) {
                System.out.println("file = [" + file + "], countBytes = [" + countBytes + "], processBytes = [" + processBytes + "]");
            }

            @Override
            public void onError(HttpResponse response, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onSuccess(HttpResponse response) {
                System.out.println("Test.onSuccess");
            }
        });
    }
}

