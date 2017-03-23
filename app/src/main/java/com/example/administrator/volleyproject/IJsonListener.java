package com.example.administrator.volleyproject;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public interface IJsonListener<M> {
    void onSuccess(M m);

    void onErro();
}
