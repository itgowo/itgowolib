package com.itgowo.http;

public interface onUploadFileCallbackListener extends onCallbackListener {
    void onProcess(String file, int countBytes, int processBytes);
}
