package com.itgowo.itgowolib;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * lujianchao
 * Github：https://github.com/hnsugar
 * WebSite: http://itgowo.com
 * QQ:1264957104
 */
public class itgowoNetTool {
    private onRequestDataListener requestListener;

    public void demo() {
        itgowo.netTool().initHttpClient(new itgowoNetTool.onRequestDataListener() {
            @Override
            public void onRequest(String url, Map head, String body, itgowoNetTool.onRequestDataListener onRequestDataListener, itgowoNetTool.onReceviceDataListener listener) {
                // TODO: 2018/4/18 开发者实现网络请求，拿到数据后调用onRequestComplete（）做Json转换，或者直接仿照源码通过getReceiveDataType(listener)获取泛型手动解析
                itgowo.netTool().onRequestComplete("request", "{\"age\":22,\"name\":\"aaaadfa\"}", listener);
            }
        });
        Map map = new HashMap();
        map.put("aaa", 111);
        map.put("bbb", 222);
        map.put("ccc", 333);
        itgowo.netTool().Request("url", map, "requestbody", new itgowoNetTool.onReceviceDataListener() {
            @Override
            public void onResult(String requestStr, String responseStr, Object result) {
                System.out.println("requestStr = [" + requestStr + "], responseStr = [" + responseStr + "], result = [" + result + "]");
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    public onRequestDataListener getRequestListener() {
        return requestListener;
    }

    public itgowoNetTool initHttpClient(onRequestDataListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }

    public void Request(String url, Map head, String body, onReceviceDataListener listener) {
        try {
            requestListener.onRequest(url, head, body, requestListener, listener);
        } catch (Throwable e) {
            listener.onError(e);
        }
    }

    public void onRequestComplete(String requestBody, String resultBody, onReceviceDataListener listener) {
        try {
            Object o = itgowo.jsonTool().JsonToObject(resultBody, getReceiveDataType(listener));
            listener.onResult(requestBody, resultBody, o);
        } catch (Throwable e) {
            listener.onError(e);
        }
    }

    public Type getReceiveDataType(onReceviceDataListener listener) {
        return itgowoClassTool.getObjectParameterizedType(listener);
    }


    public interface onReceviceDataListener<resultType> {
        void onResult(String requestStr, String responseStr, resultType result);

        void onError(Throwable throwable);
    }

    public interface onRequestDataListener {
        void onRequest(String url, Map head, String body, onRequestDataListener onRequestDataListener, onReceviceDataListener listener);
    }
}
