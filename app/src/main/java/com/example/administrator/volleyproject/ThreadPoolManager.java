package com.example.administrator.volleyproject;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public class ThreadPoolManager {
    private static ThreadPoolManager ourInstance = new ThreadPoolManager();
    private ThreadPoolExecutor threadPoolExecutor;
    private LinkedBlockingQueue<Future<?>> service=new LinkedBlockingQueue<>();

    public static ThreadPoolManager getInstance() {
        return ourInstance;
    }

    private ThreadPoolManager() {
        threadPoolExecutor=new ThreadPoolExecutor(4,10,10,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),handler);
        threadPoolExecutor.execute(runnable);
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
                while (true) {
                    FutureTask futureTask=null;
                    try {
                        Log.i("dongnao","请求队列 有 "+service.size()+" 请求");
                        futureTask= (FutureTask) service.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(futureTask!=null){
                        threadPoolExecutor.execute(futureTask);
                    }
                }
            }

    };
    public <T> void excute(FutureTask<T> futureTask)
    {
        if(futureTask!=null)
        {
            try {
                service.put(futureTask);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                service.put(new FutureTask<Object>(r,null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
