package com.example.administrator.volleyproject;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class HttpTask implements  Runnable {
    private IHttpService httpService;

    public <T> HttpTask(T requestInfo,String url,IHttpListener httpListener) {
        this.httpService=new JsonHttpService();
        httpService.setUrl(url);
        httpService.setHttpCallBack(httpListener);
        if(requestInfo!=null) {
            String requestContent= JSON.toJSONString(requestInfo);
            try {
                this.httpService.setRequestData(requestContent.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        httpService.excute();
    }
}
