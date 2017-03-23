package com.example.administrator.volleyproject;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public interface IHttpListener {
    void onSuccess(InputStream inputStream);

    void onErro();

}
