package com.itgowo.socket;

public interface onSocketMessageListener {
    void onConnectedServer();

    void onReadable(ByteBuffer byteBuffer);

    void onWritable();

    void onError(String errormsg,Exception e);

    void onStop();
}
