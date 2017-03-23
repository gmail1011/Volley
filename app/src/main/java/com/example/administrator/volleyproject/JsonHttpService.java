package com.example.administrator.volleyproject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class JsonHttpService implements IHttpService {
    IHttpListener httpListener;
    private HttpClient httpClient=new DefaultHttpClient();
    String url;
    private byte[] requestData;
    private HttpRequestBase httpRequestBase;
    private HttpResponceHandle httpResponceHandle=new HttpResponceHandle();
    @Override
    public void setUrl(String url) {
        this.url=url;
    }

    @Override
    public void excute() {
        httpRequestBase=new HttpPost(url);
        if(requestData!=null)
        {
            ByteArrayEntity byteArrayEntity=new ByteArrayEntity(requestData);
            ((HttpPost)httpRequestBase).setEntity(byteArrayEntity);
        }
        try {
            this.httpClient.execute(httpRequestBase, httpResponceHandle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener=httpListener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData=requestData;
    }
    private class HttpResponceHandle extends BasicResponseHandler {

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int code=response.getStatusLine().getStatusCode();
            if(code==200)
            {
                HttpEntity httpEntity=response.getEntity();
                if(httpListener!=null)
                {
                    try {
                        InputStream inputStream=httpEntity.getContent();
                        httpListener.onSuccess(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }else {
                if(httpListener!=null)
                {
                    httpListener.onErro();
                }

            }
            return null;
        }
    }

}
