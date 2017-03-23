package com.example.administrator.volleyproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private String url="http://apis.juhe.cn/mobile/get";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void request(View view)
    {
        for (int i=0;i<50;i++)
        {
            Volley.sendRequest(null, url,Responese.class,  new IJsonListener<Responese>() {
                @Override
                public void onSuccess(Responese responese) {
                    Log.i("dongnao","responce:  "+responese.toString());
                }

                @Override
                public void onErro() {

                }
            });
        }
    }
}
