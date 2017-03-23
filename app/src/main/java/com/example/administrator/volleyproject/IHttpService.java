package com.example.administrator.volleyproject;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public interface IHttpService {
    void setUrl(String url);

    void excute();

    void setHttpCallBack(IHttpListener httpListener);

    void setRequestData(byte[] requestData);
}
