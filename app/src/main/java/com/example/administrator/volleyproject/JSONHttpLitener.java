package com.example.administrator.volleyproject;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class JSONHttpLitener<M> implements IHttpListener {
    private Class<M> reponceClass;
    private IJsonListener<M> jsonListener;
    Handler handler=new Handler(Looper.getMainLooper());
    public JSONHttpLitener(Class<M> reponceClass, IJsonListener<M> jsonListener) {
        this.reponceClass = reponceClass;
        this.jsonListener = jsonListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content=getContent(inputStream);
        final M response= JSON.parseObject(content,reponceClass);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(jsonListener!=null)
                {
                    jsonListener.onSuccess(response);
                }
            }
        });
    }

    @Override
    public void onErro() {


    }
    private String getContent(InputStream inputStream) {
        String content=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();

            String line = null;

            try {

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

            } catch (IOException e) {

                System.out.println("Error=" + e.toString());

            } finally {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    System.out.println("Error=" + e.toString());

                }

            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
