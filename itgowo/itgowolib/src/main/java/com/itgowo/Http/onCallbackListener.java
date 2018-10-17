package com.itgowo.http;

public interface onCallbackListener {
    void onError(HttpResponse response, Exception e);

    void onSuccess(HttpResponse response);
}
