package com.itgowo.http;

public interface onCallbackListener {
    void onError(Response response, Exception e);

    void onSuccess(Response response);
}
