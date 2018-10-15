package com.itgowo.Http;

public interface onCallbackListener {
    void onError(Response response, Exception e);

    void onSuccess(Response response);
}
