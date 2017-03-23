package com.example.administrator.volleyproject;

import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class Volley {
    public  static <T,M> void sendRequest(T  requestInfo,String url
            ,Class<M>  responce,IJsonListener<M>  httpCallable  )
    {
        IHttpListener httpListener=new JSONHttpLitener<>(responce,httpCallable);
        HttpTask httpTask=new HttpTask(requestInfo,url,httpListener);
        ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask,null));
    }

}
