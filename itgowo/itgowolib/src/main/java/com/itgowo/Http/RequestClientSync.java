package com.itgowo.http;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class RequestClientSync implements Callable<HttpResponse> {
    private URL url;
    private String reqestStr;
    private String requestMethod = "POST";
    private int timeout = 15000;

    public RequestClientSync(String url, String method, int timeout, String reqestStr) throws MalformedURLException {
        this.reqestStr = reqestStr == null ? "" : reqestStr;
        this.requestMethod = method;
        this.timeout = timeout;
        if (url != null) {
            if (!url.startsWith("http://") & !url.startsWith("https://")) {
                url = "http://" + url;
            }
        }
        this.url = new URL(url);

    }

    @Override
    public HttpResponse call() throws Exception {
        HttpURLConnection httpConn = null;
        httpConn = (HttpURLConnection) url.openConnection();
        HttpResponse response = new HttpResponse();
        //设置参数
        httpConn.setDoOutput(true);     //需要输出
        httpConn.setDoInput(true);      //需要输入
        httpConn.setUseCaches(false);   //不允许缓存
        httpConn.setRequestMethod(requestMethod);      //设置POST方式连接
        httpConn.setReadTimeout(timeout);

        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");

        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();

        //建立输入流，向指向的URL传入参数
        BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(httpConn.getOutputStream()));
        bos.write(reqestStr);
        bos.flush();

        //获得响应状态
        final int resultCode = httpConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = httpConn.getInputStream();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, count);
            }
//                StringBuffer sb = new StringBuffer();
//                String readLine = new String();
//                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
//                while ((readLine = responseReader.readLine()) != null) {
//                    sb.append(readLine).append("\n");
//                }
//                responseReader.close();
            return response.setBody(outputStream.toByteArray()).setRequest(reqestStr).setMethod(requestMethod);
        }
        throw new Exception("http code:" + resultCode);
    }
}
